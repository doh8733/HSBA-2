package com.beetech.hsba.ui.home

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.beetech.hsba.R
import com.beetech.hsba.base.BaseFragment
import com.beetech.hsba.base.adapter.page.HomePagerAdapter
import com.beetech.hsba.base.adapter.page.ImageSlideAdapter
import com.beetech.hsba.entity.slider.Photo
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*
import java.sql.Time
import java.util.*
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var mViewPagerAdapter: HomePagerAdapter
    private val imageSlideAdapter: ImageSlideAdapter by lazy {
        ImageSlideAdapter()
    }
    private lateinit var handler: Handler
    private var timer = Timer()
    private var currentPosition = 0
    override fun backFromAddFragment() {

    }

    override val layoutId: Int
        get() = R.layout.home_fragment

    override fun initView() {
        mViewPagerAdapter = HomePagerAdapter(this)
        view_page.apply {
            adapter = mViewPagerAdapter
            offscreenPageLimit = 2
            isUserInputEnabled = false

        }
        initBorderItemPage1()
        view_slide.apply {
            adapter = imageSlideAdapter
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        }
        circle_indicator.attachTo(view_slide)

        setUpTransform()
        onPageChangeCallback()
    }

    override fun initData() {
        imageSlideAdapter.listPhoto = listImage()
        handler = Handler(Looper.getMainLooper())
    }

    override fun initListener() {
        btn_chuyen_khoa.setOnClickListener {
            initEffectSelectItem1()
        }
        btn_dich_vu.setOnClickListener {
            initEffectSelectItem2()
        }

    }

    override fun backPressed(): Boolean {

        return true
    }


    private fun initBorderItemPage1() {
        view_page.currentItem = 0
        btn_chuyen_khoa.setBackgroundResource(R.drawable.custom_select_item1)
        btn_dich_vu.setBackgroundResource(R.drawable.strok_bottom_right)
        btn_dich_vu.elevation = 10F

    }


    private fun initEffectSelectItem1() {
        view_page.currentItem = 0
        btn_dich_vu.elevation = 10F
        btn_chuyen_khoa.setBackgroundResource(R.drawable.custom_select_item1)
        btn_dich_vu.setBackgroundResource(R.drawable.strok_bottom_right)
    }

    private fun initEffectSelectItem2() {
        view_page.currentItem = 1
        btn_chuyen_khoa.setBackgroundResource(R.drawable.strok_bottom_left)
        btn_dich_vu.setBackgroundResource(R.drawable.custom_select_item2)
        btn_chuyen_khoa.elevation = 10F

    }

    private fun listImage(): MutableList<Photo> {
        val list = mutableListOf<Photo>()
        list.add(Photo(id = R.mipmap.anh))
        list.add(Photo(id = R.mipmap.anh2))
        list.add(Photo(id = R.mipmap.anh3))

        return list
    }

    private fun setUpTransform() {
        val transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(18))
        transform.addTransformer { page, positon ->
            val r = 1 - abs(positon)
        }
        view_slide.setPageTransformer(transform)

    }


    private fun onPageChangeCallback() {
        view_slide.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Looper.getMainLooper().let {
                    Handler(it).postDelayed({
                        if (position < listImage().size - 1) {
                            view_slide.setCurrentItem(view_slide.currentItem+1,true)
                        } else {
                            view_slide.setCurrentItem(0,true)
                        }
                    }, 2500)
                }

            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}