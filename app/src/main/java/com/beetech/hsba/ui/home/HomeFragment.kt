package com.beetech.hsba.ui.home

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.beetech.hsba.R
import com.beetech.hsba.base.BaseFragment
import com.beetech.hsba.base.adapter.RecyclerViewAdapter.Companion.TAG
import com.beetech.hsba.adapter.ImageSlideAdapter
import com.beetech.hsba.adapter.ServiceAndSpecialtyAdapter
import com.beetech.hsba.entity.services.Services
import com.beetech.hsba.entity.slider.Photo
import com.beetech.hsba.entity.specialty.Specialty
import com.beetech.hsba.ui.login.LoginViewModel
import com.beetech.hsba.utils.Define
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private val viewModel: HomeViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val imageSlideAdapter: ImageSlideAdapter by lazy {
        ImageSlideAdapter()
    }
    private val serviceAdapter = ServiceAndSpecialtyAdapter()
    private lateinit var handler: Handler
    override fun backFromAddFragment() {

    }

    private var listSpecialty = mutableListOf<Specialty>()
    private var listServices = mutableListOf<Services>()


    override val layoutId: Int
        get() = R.layout.home_fragment

    override fun initView() {
        initBorderPage()
        marginStatusBar(listOf(img_avatar, txv_name, img_notify, txv_notify))
        vp_slider.apply {
            adapter = imageSlideAdapter
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
        }

        initAvatar()
        setUpTransform()
        onPageChangeCallback()
    }

    override fun initData() {
        imageSlideAdapter.listPhoto = listImage()
        handler = Handler(Looper.getMainLooper())

        getService()
        getSpecialty()
    }

    override fun initListener() {
        txv_chuyen_khoa.setOnClickListener {
            initEffectSelectItem1()
        }
        txv_dich_vu.setOnClickListener {
            initEffectSelectItem2()
        }
    }

    override fun backPressed(): Boolean {
        return true
    }

    private fun initAvatar() {
        loginViewModel.getDataUser().let { data ->
            Glide.with(requireContext()).load(Define.Link.LINK_IMG + data?.avatar)
                .placeholder(R.mipmap.ic_launcher).into(img_avatar)
            txv_name.text = data?.name
        }
    }
    private fun initBorderPage() {
        txv_chuyen_khoa.setBackgroundResource(R.drawable.custom_select_item1)
        txv_dich_vu.setBackgroundResource(R.drawable.strok_bottom_right)
        txv_dich_vu.elevation = 10F
        container_tab.setBackgroundResource(R.drawable.border_sub_layout_page_chuyen_khoa)
    }


    private fun initEffectSelectItem1() {
        txv_dich_vu.elevation = 10F
        txv_chuyen_khoa.setBackgroundResource(R.drawable.custom_select_item1)
        txv_dich_vu.setBackgroundResource(R.drawable.strok_bottom_right)
        container_tab.setBackgroundResource(R.drawable.border_sub_layout_page_chuyen_khoa)
        rcv_specialty_or_service.apply {
            serviceAdapter.lPhoto = listSpecialty.toMutableList()
            adapter = serviceAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initEffectSelectItem2() {
        txv_chuyen_khoa.setBackgroundResource(R.drawable.strok_bottom_left)
        txv_dich_vu.setBackgroundResource(R.drawable.custom_select_item2)
        txv_chuyen_khoa.elevation = 10F
        container_tab.setBackgroundResource(R.drawable.border_sub_layout_page_dich_vu)
        rcv_specialty_or_service.apply {
            serviceAdapter.lPhoto = listServices.toMutableList()
            adapter = serviceAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
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
            page.scaleY = 0.84F + r * 0.14F
        }
        vp_slider.setPageTransformer(transform)
    }

    private fun onPageChangeCallback() {
        vp_slider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Handler().postDelayed({
                    if (position < listImage().size - 1) {
                        vp_slider.currentItem = vp_slider.currentItem + 1
                    } else {
                        vp_slider.currentItem = 0
                    }
                }, 2500)
            }

            override fun onPageScrollStateChanged(state: Int) {
                changeColor()
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                changeColor()
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }

    private fun changeColor() {
        when (vp_slider.currentItem) {
            0 -> {
                img_indicator1.setBackgroundResource(R.drawable.color_indicator)
                img_indicator2.setBackgroundResource(R.drawable.color_indicator_unselected)
                img_indicator3.setBackgroundResource(R.drawable.color_indicator_unselected)
            }
            1 -> {
                img_indicator1.setBackgroundResource(R.drawable.color_indicator_unselected)
                img_indicator2.setBackgroundResource(R.drawable.color_indicator)
                img_indicator3.setBackgroundResource(R.drawable.color_indicator_unselected)
            }
            2 -> {
                img_indicator1.setBackgroundResource(R.drawable.color_indicator_unselected)
                img_indicator2.setBackgroundResource(R.drawable.color_indicator_unselected)
                img_indicator3.setBackgroundResource(R.drawable.color_indicator)
            }
        }
    }

    private var boolean = true
    private fun getSpecialty() {
        viewModel.getSpecialty()
        viewModel.data.observe(this) {
            handleListResponse(it)
        }
    }

    private fun getService() {
        viewModel.getService()
        viewModel.dataService.observe(viewLifecycleOwner) {
            handleListResponse(it)
        }
    }

    override fun <U> getListResponse(data: List<U>?) {
        if (data?.firstOrNull() is Specialty) {
            val result = data as List<Specialty>
            listSpecialty.addAll(result)
            rcv_specialty_or_service.apply {
                serviceAdapter.lPhoto = listSpecialty.toMutableList()
                adapter = serviceAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        } else if (data?.firstOrNull() is Services) {
            val result = data as List<Services>
            listServices.addAll(result)
        }
    }

    override fun handleNetworkError(throwable: Throwable?, isShowDialog: Boolean) {
        Toast.makeText(requireContext(), R.string.not_internet, Toast.LENGTH_SHORT).show()
    }

}