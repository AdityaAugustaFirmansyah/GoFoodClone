package com.example.domain

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