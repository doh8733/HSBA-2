package com.beetech.hsba.ui.medical_history

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.beetech.hsba.base.BaseViewModel
import com.beetech.hsba.base.entity.BaseListLoadMoreResponse
import com.beetech.hsba.entity.medical_history.MedicalHistory
import com.beetech.hsba.extension.ListLoadMoreResponse
import com.beetech.hsba.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MediacalHistoryViewModel @Inject constructor(
    var repository: Repository,
    val application: Application
) : BaseViewModel() {

    var data: ListLoadMoreResponse<MedicalHistory> = MutableLiveData()

    fun getDataPage(indexPage: Int, token: String) {
        repository.getDataLoadMore(indexPage, token).doOnSubscribe {
            data.value = BaseListLoadMoreResponse<MedicalHistory>().loading()
        }.subscribe({
            if (it.currentPage >it.totalPage) {
                data.value = BaseListLoadMoreResponse<MedicalHistory>().success(it.data, isRefresh = true, isLoadmore = false)
            } else {
                data.value = BaseListLoadMoreResponse<MedicalHistory>().success(it.data, isRefresh = true, isLoadmore = true)
            }
            Toast.makeText(application.applicationContext, "Thanh cong", Toast.LENGTH_SHORT).show()
        }, {
            data.value = BaseListLoadMoreResponse<MedicalHistory>().error(it)
        })
    }
}