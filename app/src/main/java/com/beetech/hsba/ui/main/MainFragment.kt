package com.beetech.hsba.ui.main


import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.beetech.hsba.R
import com.beetech.hsba.base.BaseFragment
import com.beetech.hsba.base.adapter.page.NavigatioPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_main.*



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
            clipToPadding = false
            clipChildren = false

        }
        setUpNavigationBottom()


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