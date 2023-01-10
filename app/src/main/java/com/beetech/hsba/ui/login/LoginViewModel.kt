package com.beetech.hsba.ui.login

import android.app.Application
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.beetech.hsba.base.BaseViewModel
import com.beetech.hsba.base.adapter.RecyclerViewAdapter.Companion.TAG
import com.beetech.hsba.base.entity.BaseObjectResponse
import com.beetech.hsba.di.module.SharedPreferencesModule
import com.beetech.hsba.entity.login.LoginRequest
import com.beetech.hsba.entity.login.User
import com.beetech.hsba.extension.ObjectResponse
import com.beetech.hsba.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repository: Repository,
    val application: Application,
    private val sharedPreferencesModule: SharedPreferencesModule
) :
    BaseViewModel() {
    var data: ObjectResponse<User> = MutableLiveData()

    fun validateLogin(userName: String, password: String) {
        if (userName.isEmpty() || password.isEmpty()) {
            Toast.makeText(application.applicationContext, "Vui lòng nhập đủ dữ liệu", Toast.LENGTH_SHORT).show()
            return
        }
        if (!userName.isValidEmail()) {
            Toast.makeText(application.applicationContext, "Sai định dạng email", Toast.LENGTH_SHORT).show()
            return
        }
        if (userName.isValidEmail() && userName.isNotEmpty() && password.isNotEmpty()) {
            posLogin(userName, password)
        }
    }

    private fun posLogin(userName: String, password: String) {
        repository.login(
            username = userName, password = password
        ).doOnSubscribe {
            data.value = BaseObjectResponse<User>().loading()
        }.subscribe({
            data.value = it.data?.let { BaseObjectResponse<User>().success(it) }
            saveDataLogin(it.data!!)
        }, {
            data.value = BaseObjectResponse<User>().error(it, true)
            Log.e(TAG, "posLogin: ${it.message}")
        })
    }

    private fun saveDataLogin(data: User) {
        sharedPreferencesModule.pushPrefsDataTag(data)
    }
    fun getDataUser() :User?{
        return sharedPreferencesModule.getPrefsDataUserTag()
    }

    fun sessionLogin(loginRequest: LoginRequest) {
        sharedPreferencesModule.pushPrefsSessionTag(loginRequest)
    }

    fun getSessionLogin() :String{
        return sharedPreferencesModule.getPrefsSessionTag().toString()
    }


    private fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}