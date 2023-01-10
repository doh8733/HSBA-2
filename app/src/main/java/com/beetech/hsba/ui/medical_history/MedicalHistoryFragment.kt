package com.beetech.hsba.ui.medical_history

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.beetech.hsba.R
import com.beetech.hsba.base.BaseFragment
import com.beetech.hsba.base.adapter.EndlessLoadingRecyclerViewAdapter
import com.beetech.hsba.base.adapter.RecyclerViewAdapter.Companion.TAG
import com.beetech.hsba.adapter.MedicalHistoryAdapter
import com.beetech.hsba.entity.medical_history.MedicalHistory
import com.beetech.hsba.ui.login.LoginViewModel
import com.beetech.hsba.utils.Define
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_medical_history.*

@AndroidEntryPoint
class MedicalHistoryFragment : BaseFragment() {
    private val mediacalHistoryViewModel: MediacalHistoryViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    override fun backFromAddFragment() {
    }

    private val medicalHistoryAdapter: MedicalHistoryAdapter by lazy {
        MedicalHistoryAdapter(requireContext()) {}
    }
    var token = ""
    private var index = 1
    override val layoutId: Int
        get() = R.layout.fragment_medical_history

    override fun initView() {
        marginStatusBar(listOf(img_back,txv_title_lskb,img_add))
        getToken()
        endless_rc_view.apply {
            setAdapter(medicalHistoryAdapter)
            setListLayoutManager(LinearLayoutManager.VERTICAL)
            setOnRefreshListener() {
                initRefreshPage()
            }
        }
        endless_rc_view.setOnLoadingMoreListener(object :
            EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener {
            override fun onLoadMore() {
                getLoadMore()
            }

        })
    }

    override fun initData() {
        getDataMediacalHistory(index, token)
    }

    override fun initListener() {
    }

    override fun backPressed(): Boolean {
        return true
    }

    private fun getLoadMore() {
        index += 1
        val page = index++
        medicalHistoryAdapter.showLoadingItem(false)
        mediacalHistoryViewModel.getDataPage(page, token)
    }

    private fun initRefreshPage() {
        index = 1
        endless_rc_view.enableRefresh(false)
        medicalHistoryAdapter.clear()
        mediacalHistoryViewModel.getDataPage(index, token)
    }

    private fun getDataMediacalHistory(currentPage: Int, token: String) {
        mediacalHistoryViewModel.getDataPage(currentPage, token)
        mediacalHistoryViewModel.data.observe(viewLifecycleOwner) {
            handleLoadMoreResponse(it)
        }
    }

    override fun getListResponse(data: List<*>?, isRefresh: Boolean, canLoadmore: Boolean) {
        super.getListResponse(data, isRefresh, canLoadmore)
        medicalHistoryAdapter.enableLoadingMore(canLoadmore)
        if (data?.firstOrNull() is MedicalHistory) {
            val result = data as List<MedicalHistory>
            medicalHistoryAdapter.hideLoadingItem()
            if (isRefresh) {
                medicalHistoryAdapter.refresh(result)
            } else {
                medicalHistoryAdapter.hideLoadingItem()
                medicalHistoryAdapter.addModel(result, false)
            }
        } else medicalHistoryAdapter.hideLoadingItem()
    }

    private fun getToken() {
        token = (Define.KeyPrefs.BEARER + loginViewModel.getDataUser()?.accessToken) ?: ""
    }


}