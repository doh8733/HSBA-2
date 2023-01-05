package com.beetech.hsba.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.preference.Preference
import android.preference.PreferenceFragment
import android.provider.CalendarContract.Colors
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.Settings.Global.getString
import android.provider.Settings.Secure.getString
import android.provider.Settings.System.getString
import android.text.Editable
import android.text.PrecomputedText
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.SharedPreferencesCompat
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.fragment.app.viewModels
import com.beetech.hsba.R
import com.beetech.hsba.base.BaseFragment
import com.beetech.hsba.base.adapter.RecyclerViewAdapter.Companion.TAG
import com.beetech.hsba.base.custom.HSBALoadingDialog
import com.beetech.hsba.base.entity.BaseObjectResponse
import com.beetech.hsba.entity.LoginRequest
import com.beetech.hsba.entity.login.Data
import com.beetech.hsba.extension.toast
import com.beetech.hsba.ui.home.HomeFragment
import com.beetech.hsba.utils.Define
import com.beetech.hsba.utils.Define.Database.User.EMAIL
import com.beetech.hsba.utils.Define.Database.User.PASSWORD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import java.security.cert.Extension

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
                username = til_userName.editText?.text.toString()
                password = til_password.editText?.text.toString()
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
        getVC().replaceFragment(HomeFragment::class.java)
        Log.e(TAG, "getObjectResponse: $data")
        sessionLogin()
    }

    private fun sessionLogin() {
        context?.getSharedPreferences("SESSION", Context.MODE_PRIVATE).let {
            val editor = it?.edit()
            editor?.putString(EMAIL, til_userName.editText?.text.toString().trim())
            editor?.putString(PASSWORD, til_password.editText?.text.toString().trim())
            editor?.apply()
        }
    }
}