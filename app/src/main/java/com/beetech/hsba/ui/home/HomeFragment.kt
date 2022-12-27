package com.beetech.hsba.ui.home

import androidx.fragment.app.viewModels
import com.beetech.hsba.R
import com.beetech.hsba.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private  val viewModel: HomeViewModel by viewModels()


    override fun backFromAddFragment() {
        
    }

    override val layoutId: Int
        get() = R.layout.home_fragment

    override fun initView() {
        
    }

    override fun initData() {
        
    }

    override fun initListener() {
        
    }

    override fun backPressed(): Boolean {

        return true
    }
}