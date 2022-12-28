package com.beetech.hsba.ui.login

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
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
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(val repository: Repository, val context: Context) :
    BaseViewModel() {
    var data: ObjectResponse<LoginResponse> = MutableLiveData()

    fun validateLogin(userName: String, password: String) {
        if (userName.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Vui lòng nhập đủ dữ liệu", Toast.LENGTH_SHORT).show()
            return
        }
        if (!userName.isValidEmail()) {
            Toast.makeText(context, "Sai định dạng email", Toast.LENGTH_SHORT).show()
            return
        }
        if (userName.isValidEmail() && userName.isNotEmpty() && password.isNotEmpty()){
            posLogin(userName, password)
        }
    }

   private fun posLogin(userName: String, password: String) {
        repository.login(
            username = userName, password = password).subscribe(object : SingleObserver<BaseObjectResponse<LoginResponse>>{
            override fun onSubscribe(d: Disposable) {
                data.value = BaseObjectResponse<LoginResponse>().loading()
            }

            override fun onSuccess(t: BaseObjectResponse<LoginResponse>) {
//                data.value = it.data?.let { it1 ->
//                    BaseObjectResponse<LoginResponse>().success(data = it1)
//                    }}
               data.value=  t.data?.let { BaseObjectResponse<LoginResponse>().success(it) }
                Log.e(TAG, "onSuccess: $t", )
            }

            override fun onError(e: Throwable) {
                data.postValue(BaseObjectResponse<LoginResponse>().error(e,true))
            }

        })


    }

    fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}