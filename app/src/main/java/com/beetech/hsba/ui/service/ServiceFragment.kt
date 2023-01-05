package com.beetech.hsba.ui.service

import androidx.recyclerview.widget.LinearLayoutManager
import com.beetech.hsba.R
import com.beetech.hsba.base.BaseFragment
import com.beetech.hsba.base.adapter.page.TabPageAdapter
import com.beetech.hsba.entity.ChuyenKhoa
import kotlinx.android.synthetic.main.fragment_service.*

class ServiceFragment : BaseFragment() {
    override fun backFromAddFragment() {
    }

    private val listDichVu = mutableListOf<ChuyenKhoa>()
    private val dichVuAdapter :TabPageAdapter by lazy {
        TabPageAdapter()
    }
    override val layoutId: Int
        get() = R.layout.fragment_service

    override fun initView() {
        rc_view_dich_vu.apply {
            adapter = dichVuAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun initData() {
        dichVuAdapter.lPhoto = getDV()
    }

    override fun initListener() {
    }

    override fun backPressed(): Boolean {
        return true
    }

    private fun getDV():MutableList<ChuyenKhoa>{
        val list = mutableListOf<ChuyenKhoa>()
        list.add(ChuyenKhoa(R.drawable.ic_kham_tong_quat,"Khám tổng quát"))
        list.add(ChuyenKhoa(R.drawable.ic_tam_soat_ung_,"Tầm soát ung thư"))
        list.add(ChuyenKhoa(R.drawable.ic_lay_mau,"Lấy máu tại nhà"))
        list.add(ChuyenKhoa(R.drawable.ic_cham_soc_tai_nha,"Chắm sóc y tế tại nhà"))
        list.add(ChuyenKhoa(R.drawable.ic_kham_suc_khoe,"Khám sức khỏe cơ quan"))
        return list
    }

}