package com.beetech.hsba.ui.login

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beetech.hsba.R
import com.beetech.hsba.base.BaseFragment
import com.beetech.hsba.base.custom.HSBALoadingDialog
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : BaseFragment() {
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
            HSBALoadingDialog.getInstance(requireContext()).show()
            Handler().postDelayed({
                HSBALoadingDialog.getInstance(requireContext()).hidden()
            },2000)
        }
    }

    override fun backPressed(): Boolean {
        return true
    }
}