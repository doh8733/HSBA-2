package com.beetech.hsba.base

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.beetech.hsba.base.custom.HSBALoadingDialog
import com.beetech.hsba.base.entity.BaseError
import com.beetech.hsba.base.entity.BaseListLoadMoreResponse
import com.beetech.hsba.base.entity.BaseListResponse
import com.beetech.hsba.base.entity.BaseObjectResponse
import com.beetech.hsba.utils.Define


abstract class BaseFragment : Fragment() {

    private var viewController : ViewController? = null

//    private val loadingDialog : LoadingDialog by lazy { LoadingDialog(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        initView()
        initData()
        initListener()

    }

    abstract fun backFromAddFragment()

    @get: LayoutRes
    protected abstract val layoutId :  Int
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initListener()
    abstract fun backPressed() : Boolean

    fun setVC(viewController: ViewController) {
        this.viewController = viewController
    }

    fun getVC() : ViewController {
        if(viewController == null){
            viewController =  (activity as BaseActivity).getViewController()
        }

        return viewController!!
    }

    protected open fun handleListResponse(response: BaseListResponse<*>) {
        when (response.type) {
            Define.ResponseStatus.LOADING -> showLoading()
            Define.ResponseStatus.SUCCESS -> {
                hideLoading()
                getListResponse(response.data)
            }
            Define.ResponseStatus.ERROR -> {
                hideLoading()
                if (response.isShowingError) {
                    handleNetworkError(response.error, true)
                } else {
                    handleValidateError(response.error as? BaseError?)
                }
            }
        }
    }

    protected open fun <U> handleObjectResponse(response: BaseObjectResponse<U>) {
        when (response.type) {
            Define.ResponseStatus.LOADING -> showLoading()
            Define.ResponseStatus.SUCCESS -> {
                hideLoading()
                getObjectResponse(response.data)
            }
            Define.ResponseStatus.ERROR -> {
                hideLoading()
                if (response.isShowingError) {
                    handleNetworkError(response.error, true)
                } else {
                    handleValidateError(response.error as? BaseError?)
                }
            }
        }
    }

    protected open fun handleLoadMoreResponse(response: BaseListLoadMoreResponse<*>) {
        when (response.type) {
            Define.ResponseStatus.LOADING -> showLoading()
            Define.ResponseStatus.SUCCESS -> {
                getListResponse(response.data, response.isRefresh, response.isLoadmore)
                hideLoading()
            }
            Define.ResponseStatus.ERROR -> {
                hideLoading()
                if (response.isShowingError) {
                    handleNetworkError(response.error, true)
                } else {
                    if (response.error is BaseError) {
                        handleValidateError(response.error)
                    }
                }
            }
        }
    }

    open fun <U> getObjectResponse(data : U){

    }

    open fun <U> getListResponse(data: List<U>?){

    }

    protected open fun getListResponse(data: List<*>?, isRefresh: Boolean, canLoadmore: Boolean) {

    }

    protected fun showLoading(){
        HSBALoadingDialog.getInstance(requireContext()).show()
    }

    protected fun hideLoading(){
        HSBALoadingDialog.getInstance(requireContext()).hidden()
    }

    protected open fun handleNetworkError(throwable: Throwable?, isShowDialog: Boolean) {
        if (activity != null && activity is BaseActivity) {
            (activity as BaseActivity?)?.handleNetworkError(
                throwable,
                isShowDialog
            )
        }
    }

    protected open fun handleValidateError(throwable: BaseError?) {}

    open fun marginStatusBar(listView: List<View>) {
        for (i in listView) {
            val params = i.layoutParams as ConstraintLayout.LayoutParams
            params.topMargin = params.topMargin + getStatusBarHeight()
            i.layoutParams = params
        }
    }
    open fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}