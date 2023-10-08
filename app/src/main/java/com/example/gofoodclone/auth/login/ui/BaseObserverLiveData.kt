package com.example.gofoodclone.auth.login.ui

import android.view.View
import androidx.lifecycle.Observer
import com.example.domain.BaseUiState
import com.example.domain.StateView
import com.example.gofoodclone.databinding.LayoutLoadingBinding

abstract class BaseObserverLiveData<T> (val loadingBinding: LayoutLoadingBinding) : Observer<BaseUiState<T>>  {

    override fun onChanged(value: BaseUiState<T>) {
        when(value.stateView){
            StateView.LOADING -> loadingBinding.root.visibility = View.VISIBLE
            StateView.FAILED -> {
                loadingBinding.root.visibility = View.GONE
                onFailure(value.msg)
            }
            StateView.SUCCESS -> {
                loadingBinding.root.visibility = View.GONE
                if (value.data==null){
                    onFailure("Gagal")
                }else{
                    onSuccess(value.data!!)
                }
            }
        }
    }
    abstract fun onSuccess(data:T)
    abstract fun onFailure(msg:String)
}