package com.beetech.hsba.ui.home

import android.content.Context
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
import com.beetech.hsba.base.adapter.page.ImageSlideAdapter
import com.beetech.hsba.base.adapter.page.ServiceAdapter
import com.beetech.hsba.base.adapter.page.SpecialistAdapter
import com.beetech.hsba.entity.ChuyenKhoa
import com.beetech.hsba.entity.login.Data
import com.beetech.hsba.entity.services.Services
import com.beetech.hsba.entity.slider.Photo
import com.beetech.hsba.entity.specialty.Specialty
import com.beetech.hsba.utils.Define
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private val viewModel: HomeViewModel by viewModels()
    private val imageSlideAdapter: ImageSlideAdapter by lazy {
        ImageSlideAdapter()
    }
    private val specialistAdapter = SpecialistAdapter()
    private val serviceAdapter = ServiceAdapter()
    private lateinit var handler: Handler
    override fun backFromAddFragment() {

    }
    private var listSpecialty = mutableListOf<Specialty>()
    private var listServices = mutableListOf<Services>()

    override val layoutId: Int
        get() = R.layout.home_fragment

    override fun initView() {
        initBorderPage()
        marginStatusBar(listOf(img_avatar,tv_name,img_notify))
        view_slide.apply {
            adapter = imageSlideAdapter
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            isUserInputEnabled = false

        }
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

    private fun initBorderPage() {
        btn_chuyen_khoa.setBackgroundResource(R.drawable.custom_select_item1)
        btn_dich_vu.setBackgroundResource(R.drawable.strok_bottom_right)
        btn_dich_vu.elevation = 10F
        container_tab.setBackgroundResource(R.drawable.border_sub_layout_page_chuyen_khoa)
    }


    private fun initEffectSelectItem1() {
        btn_dich_vu.elevation = 10F
        btn_chuyen_khoa.setBackgroundResource(R.drawable.custom_select_item1)
        btn_dich_vu.setBackgroundResource(R.drawable.strok_bottom_right)
        container_tab.setBackgroundResource(R.drawable.border_sub_layout_page_chuyen_khoa)
        view_page.apply {
            specialistAdapter.lPhoto = listSpecialty
            adapter = specialistAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initEffectSelectItem2() {
        btn_chuyen_khoa.setBackgroundResource(R.drawable.strok_bottom_left)
        btn_dich_vu.setBackgroundResource(R.drawable.custom_select_item2)
        btn_chuyen_khoa.elevation = 10F
        container_tab.setBackgroundResource(R.drawable.border_sub_layout_page_dich_vu)
        view_page.apply {
            serviceAdapter.lPhoto = listServices
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
        view_slide.setPageTransformer(transform)

    }


    private fun onPageChangeCallback() {
        view_slide.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Looper.getMainLooper().let {
                    Handler(it).postDelayed({
                        if (position < listImage().size - 1) {
                            view_slide.currentItem = view_slide.currentItem + 1
                        } else {
                            view_slide.currentItem = 0
                        }
                    }, 2500)
                }

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
        when (view_slide.currentItem) {
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
        viewModel.dataSpecialtyRes.observe(viewLifecycleOwner) {
            it.data?.let { it1 -> listSpecialty.addAll(it1) }
            Log.e(TAG, "Specialty: $listSpecialty")
            view_page.apply {
                specialistAdapter.lPhoto = listSpecialty
                adapter = specialistAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

    }

    private fun getService() {
        viewModel.getService()
        viewModel.dataService.observe(viewLifecycleOwner) {
            handleListResponse(it)
        }
        viewModel.dataServiceRes.observe(viewLifecycleOwner) {
            it.data?.let { it1 -> listServices.addAll(it1) }
            Log.e(TAG, "services: $listServices")
        }
    }

    override fun <U> getListResponse(data: List<U>?) {


    }

    override fun handleNetworkError(throwable: Throwable?, isShowDialog: Boolean) {
        Toast.makeText(requireContext(), R.string.not_internet, Toast.LENGTH_SHORT).show()
    }


    private fun listc(): MutableList<ChuyenKhoa> {
        val list = mutableListOf<ChuyenKhoa>()
        list.add(ChuyenKhoa(R.drawable.ic_gan, "Truy???n nhi???m - Vi??m gan"))
        list.add(ChuyenKhoa(R.drawable.ic_noi_tiet, "N???i ti???t - ????i th??o ???????ng"))
        list.add(ChuyenKhoa(R.drawable.ic_tim_mach, "Tim m???ch - Huy???t ??p"))
        list.add(ChuyenKhoa(R.drawable.ic_ung_buou, "Ung b?????u"))
        list.add(ChuyenKhoa(R.drawable.ic_than, "Th???n - Ti???t ni???u"))
        list.add(ChuyenKhoa(R.drawable.ic_long, "Ti??u h??a"))
        list.add(ChuyenKhoa(R.drawable.ic_xuong_khop, "C?? x????ng kh???p"))
        list.add(ChuyenKhoa(R.drawable.ic_phoi, "H?? h???p - COPD"))
        list.add(ChuyenKhoa(R.drawable.ic_tai_mui_hong, "Tai - M??i - H???ng"))
        list.add(ChuyenKhoa(R.drawable.ic_rang, "R??ng - H??m - M???t"))
        list.add(ChuyenKhoa(R.drawable.ic_ngoai_khoa, "Ngo???i khoa"))
        return list
    }

    private fun getDV(): MutableList<ChuyenKhoa> {
        val list = mutableListOf<ChuyenKhoa>()
        list.add(ChuyenKhoa(R.drawable.ic_kham_tong_quat, "Kh??m t???ng qu??t"))
        list.add(ChuyenKhoa(R.drawable.ic_tam_soat_ung_, "T???m so??t ung th??"))
        list.add(ChuyenKhoa(R.drawable.ic_lay_mau, "L???y m??u t???i nh??"))
        list.add(ChuyenKhoa(R.drawable.ic_cham_soc_tai_nha, "Ch???m s??c y t??? t???i nh??"))
        list.add(ChuyenKhoa(R.drawable.ic_kham_suc_khoe, "Kh??m s???c kh???e c?? quan"))
        return list
    }

}