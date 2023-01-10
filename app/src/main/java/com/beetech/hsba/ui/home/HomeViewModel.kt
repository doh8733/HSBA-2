package com.beetech.hsba.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beetech.hsba.base.adapter.RecyclerViewAdapter.Companion.TAG
import com.beetech.hsba.base.entity.BaseListResponse
import com.beetech.hsba.entity.services.Services
import com.beetech.hsba.entity.specialty.Specialty
import com.beetech.hsba.extension.ListResponse
import com.beetech.hsba.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: Repository,application: Application) : ViewModel() {

  val data : ListResponse<Specialty> = MutableLiveData()
  val dataSpecialtyRes : ListResponse<Specialty> = MutableLiveData()
  val dataService : ListResponse<Services> = MutableLiveData()
  val dataServiceRes: ListResponse<Services> = MutableLiveData()

  fun getSpecialty(){
    repository.getSpecialty().doOnSubscribe {
      data.value = BaseListResponse<Specialty>().loading()
    }.subscribe({
      data.value = BaseListResponse<Specialty>().success(it.data)
      dataSpecialtyRes.value = it
    },{
      data.value = BaseListResponse<Specialty>().error(it)
    })
  }
  fun getService(){
    repository.getServices().doOnSubscribe {

    }.subscribe({
      dataService.value = BaseListResponse<Services>().success(it.data)
      dataServiceRes.value = it
    },{
      dataService.value = BaseListResponse<Services>().error(it)
    })
  }
}