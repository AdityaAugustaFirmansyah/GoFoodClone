package com.example.gofoodclone.auth.register.persentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gofoodclone.auth.domain.BaseDomain
import com.example.gofoodclone.auth.domain.DataAuth
import com.example.gofoodclone.auth.domain.LoginLoader
import com.example.gofoodclone.auth.domain.PayloadAuth
import com.example.gofoodclone.auth.login.http.usecase.Connectivity
import com.example.gofoodclone.auth.login.http.usecase.InvalidData
import com.example.gofoodclone.auth.login.persentation.BaseUiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterViewModel(val loginLoader: LoginLoader) : ViewModel() {
    val onRegister = MutableLiveData<BaseUiState<DataAuth>>()
    val onPage = MutableLiveData<Int>(1)
    fun register(payloadAuth: PayloadAuth){
        onRegister.value = BaseUiState.createInstanceLoading()
        viewModelScope.launch {
            if (payloadAuth.phoneNumber.isNullOrEmpty()) {
                onRegister.value = BaseUiState.createInstanceFailed("No Phone Wajib Di Isi")
            } else if (payloadAuth.address.isNullOrEmpty()) {
                onRegister.value = BaseUiState.createInstanceFailed("Alamat Wajib Di Isi")
            }else if (payloadAuth.houseNumber.isNullOrEmpty()){
                onRegister.value = BaseUiState.createInstanceFailed("No Rumah Wajib Di Isi")
            }else if (payloadAuth.city.isNullOrEmpty()){
                onRegister.value = BaseUiState.createInstanceFailed("Kota Wajib Di Isi")
            }else{
                loginLoader.login(payloadAuth).collect{ result->
                    when (result) {
                        is BaseDomain.Success -> onRegister.value =
                            BaseUiState.createInstanceSuccess(result.data)

                        is BaseDomain.Failure -> onRegister.value =
                            BaseUiState.createInstanceFailed(
                                when (result.throwable) {
                                    is Connectivity -> "Connectivity"
                                    is InvalidData -> "Invalid Data"
                                    else -> "Something When Worng"
                                }
                            )
                    }
                }
            }
        }
    }
    fun onNextPage(payloadAuth: PayloadAuth){
        if (payloadAuth.name.isNullOrEmpty()) {
            onRegister.value = BaseUiState.createInstanceFailed("Nama Wajib Di Isi")
        } else if (payloadAuth.email.isEmpty()) {
            onRegister.value = BaseUiState.createInstanceFailed("Email Wajib Di Isi")
        }else if (payloadAuth.password.isEmpty() && payloadAuth.password.length<=8){
            onRegister.value = BaseUiState.createInstanceFailed("Password Wajib Di Isi dan min 8 karakter")
        }else{
            onPage.value = 2
        }
    }
    fun onPrevPage(){
        onPage.value = 1
    }
}

class RegisterViewModelFactory(val loginLoader: LoginLoader):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(loginLoader) as T
    }
}