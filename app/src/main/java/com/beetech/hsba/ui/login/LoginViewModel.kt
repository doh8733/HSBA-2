package com.beetech.hsba.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.beetech.hsba.base.BaseViewModel
import com.beetech.hsba.base.adapter.RecyclerViewAdapter.Companion.TAG
import com.beetech.hsba.base.entity.BaseObjectResponse
import com.beetech.hsba.entity.login.Data
import com.beetech.hsba.entity.login.LoginResponse
import com.beetech.hsba.extension.ObjectResponse
import com.beetech.hsba.network.Repository
import com.beetech.hsba.utils.Define
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(val repository: Repository) :BaseViewModel(){
    var data : ObjectResponse<LoginResponse> = MutableLiveData()

    fun loginResponse(userName :String,password :String){
        repository.login(username =  userName, password =  password
        ).doOnSubscribe {
        data.value = BaseObjectResponse<LoginResponse>().loading()

        }.subscribe({
                data.value = it.data?.let { it1 -> BaseObjectResponse<LoginResponse>().success(data = it1) }
        },{
            data.value = BaseObjectResponse<LoginResponse>().error(throwable = it,true)
        })
    }
}