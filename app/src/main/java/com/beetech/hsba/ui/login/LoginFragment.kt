package com.beetech.hsba.ui.login

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.provider.CalendarContract.Colors
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.beetech.hsba.R
import com.beetech.hsba.base.BaseFragment
import com.beetech.hsba.base.adapter.RecyclerViewAdapter.Companion.TAG
import com.beetech.hsba.base.custom.HSBALoadingDialog
import com.beetech.hsba.base.entity.BaseObjectResponse
import com.beetech.hsba.entity.LoginRequest
import com.beetech.hsba.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    private val loginViewModel :LoginViewModel by viewModels()
    override fun backFromAddFragment() {

    }

    override val layoutId: Int
        get() = R.layout.fragment_login

    override fun initView() {
        setBorder()

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
                loginViewModel.loginResponse(username.toString(),password.toString())
                loginViewModel.data.observe(viewLifecycleOwner){
                    handleObjectResponse(it)
                }
            }
        }
    }

    override fun backPressed(): Boolean {
        return true
    }
    private fun setBorder(){
        til_userName.editText?.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                til_userName.boxStrokeColor.div(R.drawable.border_til)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                til_userName.boxStrokeColor.div(R.drawable.border_til)
            }

            override fun afterTextChanged(s: Editable?) {
                til_userName.boxStrokeColor.div(R.drawable.border_til)
            }

        })
    }
//    private fun login(userName:String,password :String){
//        loginViewModel.loginResponse(userName, password)
//        loginViewModel.data.observe(viewLifecycleOwner){
//
//        }
//    }
    override fun <U> getObjectResponse(data: U) {
        getVC().replaceFragment(HomeFragment::class.java,Bundle().apply {
            putSerializable("NAME", data.toString())
        })
    Log.e(TAG, "getObjectResponse: $data", )
    }


}