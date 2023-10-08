package com.example.gofoodclone.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.BaseDomain
import com.example.domain.BaseUiState
import com.example.domain.DataAuth
import com.example.session.usecase.SessionLocal
import kotlinx.coroutines.launch

class HomeViewModel(localLogin: SessionLocal) : ViewModel() {
    val onCheckLogin = MutableLiveData<BaseUiState<com.example.domain.DataAuth>>()
    init {
        viewModelScope.launch {
            localLogin.check().collect{result->
                when (result) {
                    is com.example.domain.BaseDomain.Success -> onCheckLogin.value =
                        BaseUiState.createInstanceSuccess(result.data)

                    is com.example.domain.BaseDomain.Failure -> onCheckLogin.value =
                        BaseUiState.createInstanceFailed("Invalid Token")
                }
            }
        }
    }
}

class HomeViewModelFactory(val localLogin: SessionLocal):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(localLogin) as T
    }
}