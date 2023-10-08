package com.example.domain.register

import kotlinx.coroutines.flow.Flow

interface RegisterLoader{
    fun login(payloadAuth: RegisterPayload):Flow<com.example.domain.BaseDomain<com.example.domain.DataAuth?>>
}