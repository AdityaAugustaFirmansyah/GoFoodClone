package com.example.gofoodclone.auth.login.domain

import com.example.gofoodclone.auth.domain.BaseDomain
import com.example.gofoodclone.auth.domain.DataAuth
import kotlinx.coroutines.flow.Flow

interface LoginLoader{
    fun login(payloadAuth: LoginPayload):Flow<BaseDomain<DataAuth?>>
}