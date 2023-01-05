package com.beetech.hsba.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.beetech.hsba.R
import com.beetech.hsba.base.BaseFragment
import com.beetech.hsba.base.adapter.page.NavigatioPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.home_fragment.*


class MainFragment : BaseFragment() {
    override fun backFromAddFragment() {

    }

    private val navigatioPagerAdapter: NavigatioPagerAdapter by lazy {
        NavigatioPagerAdapter(this)
    }
    override val layoutId: Int
        get() = R.layout.fragment_main

    override fun initView() {
        page_nav.apply {
            adapter = navigatioPagerAdapter
            offscreenPageLimit = 5
            isUserInputEnabled = false

        }
        setUpNavigationBottom()
//        TabLayoutMediator(tab_nav,page_nav){tab,page ->
//            when (page) {
//                0 -> {
//                    tab.text = getString(R.string.tab1)
//                }
//                1 -> {
//                    tab.text = getString(R.string.tab2)
//                }
//                2 -> {
//                    tab.text = getString(R.string.tab3)
//                }
//                3 -> {
//                    tab.text = getString(R.string.tab4)
//                }
//                4 -> {
//                    tab.text = getString(R.string.tab5)
//                }
//            }
//        }.attach()
//        tab_nav.apply {
//            getTabAt(0)?.setIcon(R.drawable.home)
//            getTabAt(1)?.setIcon(R.drawable.ic_nav_kham_benh)
//            getTabAt(2)?.setIcon(R.drawable.ic_nav_xet_nghiem)
//            getTabAt(3)?.setIcon(R.drawable.ic_nav_ho_so)
//            getTabAt(4)?.setIcon(R.drawable.ic_nav_tai_khoan)
//        }


    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun backPressed(): Boolean {
        return false
    }

    private fun setUpNavigationBottom() {
        tab_nav.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.nav_home -> {
                        page_nav.currentItem = 0
                    }
                    R.id.nav_kham_benh -> {
                        page_nav.currentItem = 1

                    }
                    R.id.nav_xet_nghiem -> {
                        page_nav.currentItem = 2

                    }
                    R.id.nav_ho_so -> {
                        page_nav.currentItem = 3

                    }
                    R.id.nav_tai_khoan -> {
                        page_nav.currentItem = 4

                    }

                }
                return true
            }

        })
        page_nav.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        tab_nav.menu.findItem(R.id.nav_home).isChecked = true
                    }
                    1 -> {
                        tab_nav.menu.findItem(R.id.nav_kham_benh).isChecked = true

                    }
                    2 -> {
                        tab_nav.menu.findItem(R.id.nav_xet_nghiem).isChecked = true

                    }
                    3 -> {
                        tab_nav.menu.findItem(R.id.nav_ho_so).isChecked = true

                    }
                    4 -> {
                        tab_nav.menu.findItem(R.id.nav_tai_khoan).isChecked = true

                    }

                }
            }
        })
    }
}