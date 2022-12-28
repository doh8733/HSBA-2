package com.beetech.hsba.ui.login

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.provider.CalendarContract.Colors
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.beetech.hsba.R
import com.beetech.hsba.base.BaseFragment
import com.beetech.hsba.base.adapter.RecyclerViewAdapter.Companion.TAG
import com.beetech.hsba.base.custom.HSBALoadingDialog
import com.beetech.hsba.base.entity.BaseObjectResponse
import com.beetech.hsba.entity.LoginRequest
import com.beetech.hsba.extension.toast
import com.beetech.hsba.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    private val loginViewModel: LoginViewModel by viewModels()
    override fun backFromAddFragment() {

    }

    override val layoutId: Int
        get() = R.layout.fragment_login

    override fun initView() {
        
    }

    override fun initData() {

    }

    override fun initListener() {
        btn_login.setOnClickListener {
//            HSBALoadingDialog.getInstance(requireContext()).show()
//            Handler().postDelayed({
//                HSBALoadingDialog.getInstance(requireContext()).hidden()
//            },2000)
            LoginRequest().apply {
                username = til_userName.editText?.text.toString()
                password = til_password.editText?.text.toString()
                loginViewModel.validateLogin(username!!, password!!)
                loginViewModel.data.observe(viewLifecycleOwner){
                    handleObjectResponse(it)
                }

            }
        }
    }

    override fun backPressed(): Boolean {
        return true
    }



    //    private fun login(userName:String,password :String){
//        loginViewModel.loginResponse(userName, password)
//        loginViewModel.data.observe(viewLifecycleOwner){
//
//        }
//    }
    override fun <U> getObjectResponse(data: U) {
        getVC().replaceFragment(HomeFragment::class.java)
        Log.e(TAG, "getObjectResponse: $data")
    }
}