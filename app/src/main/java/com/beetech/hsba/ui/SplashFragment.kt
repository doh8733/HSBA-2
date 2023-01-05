package com.beetech.hsba.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import com.beetech.hsba.R
import com.beetech.hsba.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import com.beetech.hsba.base.adapter.RecyclerViewAdapter.Companion.TAG
import com.beetech.hsba.ui.home.HomeFragment
import com.beetech.hsba.ui.login.LoginFragment
import com.beetech.hsba.ui.main.MainFragment
import com.beetech.hsba.utils.Define
import kotlin.math.log

@AndroidEntryPoint
class SplashFragment : BaseFragment() {
    private var email = ""
    private var password = ""

    private lateinit var sharedPreferences: SharedPreferences

    override val layoutId: Int
        get() = R.layout.splash_fragment

    override fun backFromAddFragment() {
    }

    override fun backPressed(): Boolean {
        return false
    }

    override fun initView() {


    }

    override fun initData() {
        getSessionLogin()
    }


    override fun initListener() {

        Looper.getMainLooper()?.let {
            Handler(it).postDelayed({
                if (email.isNotEmpty() && password.isNotEmpty()){
                    getVC().replaceFragment(MainFragment::class.java)
                }
                else{
                    getVC().replaceFragment(LoginFragment::class.java)
                }
            }, 1500)
        }


    }

    private fun getSessionLogin(){
        context?.getSharedPreferences("SESSION", Context.MODE_PRIVATE)?.let {
            email = it.getString(Define.Database.User.EMAIL, "").toString()
            password = it.getString(Define.Database.User.PASSWORD, "").toString()
            Log.e(TAG, "initData: $email")
        }
    }

    private val viewModel: SplashViewModel by viewModels()
}
