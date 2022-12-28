package com.beetech.hsba

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.beetech.hsba.base.BaseActivity
import com.beetech.hsba.ui.SplashFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {


    override val layoutResId: Int
        get() = R.layout.activity_main
    override val layoutId: Int
        get() = R.id.container

    override fun initListener() {
    }

    override fun initView() {
        getViewController().addFragment(SplashFragment::class.java)



    }

    override fun initData() {
    }
////and di statusbar voi navigationbar
//    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        super.onWindowFocusChanged(hasFocus)
//        if (hasFocus) hideSystemUI() else showSystemUI()
//    }
//    private fun hideSystemUI(){
//        if (Build.VERSION.SDK_INT >= 30){
//            window.insetsController?.apply {
//                hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
//            }
//        }
//        else{
//            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
//                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_FULLSCREEN
//                    )
//        }
//    }
//    private fun showSystemUI() {
//        if (Build.VERSION.SDK_INT >= 30) {
//            window.insetsController?.apply {
//                show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
//            }
//        } else {
//            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
//        }
//    }

}
