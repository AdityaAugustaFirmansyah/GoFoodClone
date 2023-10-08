package com.example.persentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.BaseUiState
import com.example.domain.Connectivity
import com.example.domain.InvalidData
import kotlinx.coroutines.launch

class RegisterViewModel(val registerLoader: com.example.domain.register.RegisterLoader) : ViewModel() {
    val onRegister = MutableLiveData<BaseUiState<com.example.domain.DataAuth>>()
    val onPage = MutableLiveData<Int>(1)
    fun register(payloadAuth: com.example.domain.register.RegisterPayload){
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
                registerLoader.login(payloadAuth).collect{ result->
                    when (result) {
                        is com.example.domain.BaseDomain.Success -> onRegister.value =
                            BaseUiState.createInstanceSuccess(result.data)

                        is com.example.domain.BaseDomain.Failure -> onRegister.value =
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
    fun onNextPage(payloadAuth: com.example.domain.register.RegisterPayload){
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

class RegisterViewModelFactory(val registerLoader: com.example.domain.register.RegisterLoader):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(registerLoader) as T
    }
}