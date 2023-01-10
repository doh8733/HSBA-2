package com.beetech.hsba.ui.login

import android.util.Log
import androidx.fragment.app.viewModels
import com.beetech.hsba.R
import com.beetech.hsba.base.BaseFragment
import com.beetech.hsba.base.adapter.RecyclerViewAdapter.Companion.TAG
import com.beetech.hsba.entity.login.LoginRequest
import com.beetech.hsba.ui.main.MainFragment
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

            LoginRequest().apply {
                username = til_userName.editText?.text.toString().trim()
                password = til_password.editText?.text.toString().trim()
                loginViewModel.validateLogin(username!!, password!!)
                loginViewModel.data.observe(viewLifecycleOwner) {
                    handleObjectResponse(it)
                }

            }
        }
    }

    override fun backPressed(): Boolean {
        return true
    }

    override fun <U> getObjectResponse(data: U) {
        getVC().replaceFragment(MainFragment::class.java)
        Log.e(TAG, "getObjectResponse: $data")
        val loginRequest = LoginRequest()
        loginRequest.username = til_userName.editText?.text.toString().trim()
        loginRequest.password = til_password.editText?.text.toString().trim()
        loginViewModel.sessionLogin(loginRequest = loginRequest)
    }


}