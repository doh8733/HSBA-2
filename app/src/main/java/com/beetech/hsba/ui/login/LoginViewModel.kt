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
import com.beetech.hsba.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val repository: Repository, val context: Application) :
    BaseViewModel() {
    var data: ObjectResponse<Data> = MutableLiveData()

    fun validateLogin(userName: String, password: String) {
        if (userName.isEmpty() || password.isEmpty()) {
            Toast.makeText(context.applicationContext, "Vui lòng nhập đủ dữ liệu", Toast.LENGTH_SHORT).show()
            return
        }
        if (!userName.isValidEmail()) {
            Toast.makeText(context.applicationContext, "Sai định dạng email", Toast.LENGTH_SHORT).show()
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
            Log.e(TAG, "onSuccess: ${it.data.toString()}", )
        },{
            Log.e(TAG, "posLogin: ${it}", )
            data.postValue(BaseObjectResponse<Data>().error(it,true))
        })







    }

    fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}