package com.beetech.hsba.base

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.beetech.hsba.R
import com.beetech.hsba.extension.toast
import com.beetech.hsba.network.NetworkCheckerInterceptor
import com.beetech.hsba.network.entity.RequestError
import com.beetech.hsba.utils.Define
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException
import java.lang.IllegalStateException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseActivity : AppCompatActivity() {

    private var viewController : ViewController? = null

    fun getViewController() : ViewController {
        if(viewController == null){
            viewController = ViewController(layoutId,supportFragmentManager)

        }
        return viewController!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        viewController = ViewController(layoutId,supportFragmentManager)

        //ham tranparent statusbar
        with(window) {
            addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            setWindowFlag(
                this@BaseActivity,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                false
            )
            window.statusBarColor = Color.TRANSPARENT
        }
       // lightStatusBar()
        initView()
        initData()
        initListener()
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
//        window.statusBarColor = Color.TRANSPARENT
    }

    override fun onBackPressed() {
        if(viewController!= null && viewController?.currentFragment!=null){
            if(viewController?.currentFragment?.backPressed() == true){
                super.onBackPressed()
            }
        }else{
            super.onBackPressed()
        }

    }

    open fun handleNetworkError(throwable: Throwable?, isShowDialog: Boolean): RequestError? {
        var requestError = RequestError()
        if (throwable is NetworkCheckerInterceptor.NoConnectivityException) {
            requestError.errorCode = (Define.Api.NO_NETWORK_CONNECTION)
            requestError.errorMessage = (throwable.message)
        } else if (throwable is HttpException) {
            val errorBody: String
            try {
                throwable.response()?.errorBody()?.let {
                    errorBody = it.string()
                    val gson = GsonBuilder().create()
                    requestError = gson.fromJson(errorBody, RequestError::class.java)
                } ?: run {
                    requestError.errorCode = (Define.Api.TIME_OUT)
                    requestError.errorMessage = (getString(R.string.error_place_holder))
                }
                //                ApiObjectResponse apiResponse = gson.fromJson(errorBody, ApiObjectResponse.class);
//                if (apiResponse != null && apiResponse.getRequestError() != null) {
//                    requestError = apiResponse.getRequestError();
//                } else {
//                    requestError.errorCode(String.valueOf(httpException.code()));
//                    requestError.errorMessage =(getString(R.string.error_place_holder));
//                }
            } catch (e: IOException) {
                requestError.errorCode = (Define.Api.TIME_OUT)
                requestError.errorMessage = (getString(R.string.error_place_holder))
            } catch (e: IllegalStateException) {
                requestError.errorCode = (Define.Api.TIME_OUT)
                requestError.errorMessage = (getString(R.string.error_place_holder))
            } catch (e: JsonSyntaxException) {
                requestError.errorCode = (Define.Api.TIME_OUT)
                requestError.errorMessage = (getString(R.string.error_place_holder))
            }
        } else if (throwable is ConnectException
            || throwable is SocketTimeoutException
            || throwable is UnknownHostException
            || throwable is IOException
        ) {
            requestError.errorCode = (Define.Api.TIME_OUT)
            requestError.errorMessage = (getString(R.string.timeout_error))
        } else {
            requestError.errorCode = (Define.Api.UNKNOWN)
            requestError.errorMessage = (getString(R.string.error_place_holder))
        }
        if (isShowDialog) {
            requestError.errorMessage?.let {
                toast(it)
            }
        }
        return requestError
    }
    open fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win: Window = activity.window
        val winParams: WindowManager.LayoutParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }


    protected abstract val layoutResId :  Int
    protected abstract val layoutId :  Int
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initListener()

//    protected fun lightStatusBar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        }
//    }

}