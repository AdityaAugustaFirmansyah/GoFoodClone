package com.example.domain.login

import kotlinx.coroutines.flow.Flow

interface LoginLoader{
    fun login(payloadAuth: LoginPayload):Flow<com.example.domain.BaseDomain<com.example.domain.DataAuth?>>
}