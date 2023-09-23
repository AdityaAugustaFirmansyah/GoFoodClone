package com.example.gofoodclone.auth.domain

sealed class BaseDomain<T>{
    data class Success<T>(val data:T):BaseDomain<T>()
    data class Failure<T>(val throwable: Throwable):BaseDomain<T>()
}