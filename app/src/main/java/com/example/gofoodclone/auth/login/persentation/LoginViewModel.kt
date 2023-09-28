package com.example.gofoodclone.auth.login.persentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gofoodclone.auth.domain.BaseDomain
import com.example.gofoodclone.auth.domain.DataAuth
import com.example.gofoodclone.auth.login.domain.LoginLoader
import com.example.gofoodclone.auth.login.domain.LoginPayload
import com.example.gofoodclone.auth.login.http.usecase.Connectivity
import com.example.gofoodclone.auth.login.http.usecase.InvalidData
import kotlinx.coroutines.launch

enum class StateView {
    LOADING,
    FAILED,
    SUCCESS;
}

data class BaseUiState<out T>(
    val stateView: StateView,
    val msg: String,
    val data: T?
) {
    companion object {
        fun <T> createInstanceSuccess(t: T?): BaseUiState<T> {
            return BaseUiState(StateView.SUCCESS, "Success", t)
        }

        fun <T> createInstanceFailed(msg: String): BaseUiState<T> {
            return BaseUiState(StateView.FAILED, msg, null)
        }

        fun <T> createInstanceLoading(): BaseUiState<T> {
            return BaseUiState(StateView.LOADING, "Loading", null)
        }
    }
}

class LoginViewModel(private val loginLoader: LoginLoader) : ViewModel() {
    val viewModelState = MutableLiveData<BaseUiState<DataAuth>>()
    fun doLogin(payloadAuth: LoginPayload) {

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
                            is BaseDomain.Success -> viewModelState.value =
                                BaseUiState.createInstanceSuccess(result.data)

                            is BaseDomain.Failure -> viewModelState.value =
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

class LoginViewModelFactory(val loginLoader: LoginLoader) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(loginLoader) as T
    }
}