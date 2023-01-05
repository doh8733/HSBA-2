package com.beetech.hsba.ui.specialist

import androidx.recyclerview.widget.LinearLayoutManager
import com.beetech.hsba.R
import com.beetech.hsba.base.BaseFragment
import com.beetech.hsba.base.adapter.page.TabPageAdapter
import com.beetech.hsba.entity.ChuyenKhoa
import kotlinx.android.synthetic.main.fragment_chuyen_khoa.*


class SpecialistFragment : BaseFragment() {

    private val chuyenKhoaAdapter :TabPageAdapter by lazy{
        TabPageAdapter()
    }
    override fun backFromAddFragment() {

    }

    override val layoutId: Int
        get() = R.layout.fragment_chuyen_khoa

    override fun initView() {
        chuyenKhoaAdapter.lPhoto = listc() as MutableList<ChuyenKhoa>
        rcView.layoutManager =LinearLayoutManager(requireContext())
        rcView.adapter = chuyenKhoaAdapter
    }

    override fun initData() {
    }

    override fun initListener() {

    }

    override fun backPressed(): Boolean {
        return true
    }
    private fun listc():List<ChuyenKhoa>{
        val list = mutableListOf<ChuyenKhoa>()
        list.add(ChuyenKhoa(R.drawable.ic_gan, "Truyền nhiễm - Viêm gan"))
        list.add(ChuyenKhoa(R.drawable.ic_noi_tiet, "Nội tiết - Đái tháo đường"))
        list.add(ChuyenKhoa(R.drawable.ic_tim_mach, "Tim mạch - Huyết áp"))
        list.add(ChuyenKhoa(R.drawable.ic_ung_buou, "Ung bướu"))
        list.add(ChuyenKhoa(R.drawable.ic_than, "Thận - Tiết niệu"))
        list.add(ChuyenKhoa(R.drawable.ic_long, "Tiêu hóa"))
        list.add(ChuyenKhoa(R.drawable.ic_xuong_khop, "Cơ xương khớp"))
        list.add(ChuyenKhoa(R.drawable.ic_phoi, "Hô hấp - COPD"))
        list.add(ChuyenKhoa(R.drawable.ic_tai_mui_hong, "Tai - Mũi - Họng"))
        return list
    }
}