package com.example.persentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.BaseUiState
import com.example.domain.Connectivity
import com.example.domain.DataAuth
import com.example.domain.InvalidData
import kotlinx.coroutines.launch


class LoginViewModel(private val loginLoader: com.example.domain.login.LoginLoader) : ViewModel() {
    val viewModelState = MutableLiveData<BaseUiState<DataAuth>>()
    fun doLogin(payloadAuth: com.example.domain.login.LoginPayload) {

        if (payloadAuth.email.isEmpty()) {
            viewModelState.value = BaseUiState.createInstanceFailed("Email Wajib Di Isi")
        } else if (payloadAuth.password.isEmpty()) {
            viewModelState.value = BaseUiState.createInstanceFailed("Password Wajib Di Isi")
        } else {
            viewModelState.value = BaseUiState.createInstanceLoading()
            viewModelScope.launch {
                loginLoader.login(payloadAuth)
                    .collect { result ->
                        when (result) {
                            is com.example.domain.BaseDomain.Success -> viewModelState.value =
                                BaseUiState.createInstanceSuccess(result.data)

                            is com.example.domain.BaseDomain.Failure -> viewModelState.value =
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
}

class LoginViewModelFactory(val loginLoader: com.example.domain.login.LoginLoader) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(loginLoader) as T
    }
}