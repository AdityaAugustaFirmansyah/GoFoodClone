package com.example.gofoodclone.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gofoodclone.auth.domain.BaseDomain
import com.example.gofoodclone.auth.domain.DataAuth
import com.example.gofoodclone.auth.domain.LoginLoader
import com.example.gofoodclone.auth.local.usecase.LocalLogin
import com.example.gofoodclone.auth.login.http.usecase.Connectivity
import com.example.gofoodclone.auth.login.http.usecase.InvalidData
import com.example.gofoodclone.auth.login.persentation.BaseUiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(localLogin: LocalLogin) : ViewModel() {
    val onCheckLogin = MutableLiveData<BaseUiState<DataAuth>>()
    init {
        viewModelScope.launch {
            localLogin.check().collect{result->
                when (result) {
                    is BaseDomain.Success -> onCheckLogin.value =
                        BaseUiState.createInstanceSuccess(result.data)

                    is BaseDomain.Failure -> onCheckLogin.value =
                        BaseUiState.createInstanceFailed("Invalid Token")
                }
            }
        }
    }
}

class HomeViewModelFactory(val localLogin: LocalLogin):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(localLogin) as T
    }
}