package com.example.gofoodclone.auth.register.domain

import com.example.gofoodclone.auth.domain.BaseDomain
import com.example.gofoodclone.auth.domain.DataAuth
import kotlinx.coroutines.flow.Flow

interface RegisterLoader{
    fun login(payloadAuth: RegisterPayload):Flow<BaseDomain<DataAuth?>>
}