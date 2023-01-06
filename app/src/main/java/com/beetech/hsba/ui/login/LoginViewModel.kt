package com.beetech.hsba.ui.login

import android.app.Application
import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.beetech.hsba.base.BaseViewModel
import com.beetech.hsba.base.adapter.RecyclerViewAdapter.Companion.TAG
import com.beetech.hsba.base.entity.BaseObjectResponse
import com.beetech.hsba.entity.login.Data
import com.beetech.hsba.extension.ObjectResponse
import com.beetech.hsba.extension.getString
import com.beetech.hsba.network.Repository
import com.beetech.hsba.utils.Define
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(val repository: Repository, val application: Application) :
    BaseViewModel() {
    var data: ObjectResponse<Data> = MutableLiveData()

    fun validateLogin(userName: String, password: String) {
        if (userName.isEmpty() || password.isEmpty()) {
            Toast.makeText(application.applicationContext, "Vui lòng nhập đủ dữ liệu", Toast.LENGTH_SHORT).show()
            return
        }
        if (!userName.isValidEmail()) {
            Toast.makeText(application.applicationContext, "Sai định dạng email", Toast.LENGTH_SHORT).show()
            return
        }
        if (userName.isValidEmail() && userName.isNotEmpty() && password.isNotEmpty()){
            posLogin(userName, password)
        }
    }

   private fun posLogin(userName: String, password: String) {
        repository.login(
            username = userName, password = password).doOnSubscribe {
            data.value = BaseObjectResponse<Data>().loading()
        }.subscribe({
            data.value=  it.data?.let { BaseObjectResponse<Data>().success(it) }
             saveDataLogin(it.data!!)
        },{
            data.value = BaseObjectResponse<Data>().error(it,true)
            Log.e(TAG, "posLogin: ${it.message}", )
        })
    }

     private fun  saveDataLogin(data: Data){
        application.getSharedPreferences("DATA",Context.MODE_PRIVATE)?.let {
            val editor = it.edit()
            editor.putString(Define.Database.User.DATA_USER,Gson().toJson(data))
            Log.e(TAG, "saveDataLogin: ${Gson().toJson(data)}", )
            editor.apply()
        }
    }

    fun sessionLogin(userName: String,password: String) {
        application.getSharedPreferences("SESSION", Context.MODE_PRIVATE).let {
            val editor = it?.edit()
            editor?.putString(Define.Database.User.EMAIL, userName)
            editor?.putString(Define.Database.User.PASSWORD, password)
            editor?.apply()
        }
    }


    private fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}