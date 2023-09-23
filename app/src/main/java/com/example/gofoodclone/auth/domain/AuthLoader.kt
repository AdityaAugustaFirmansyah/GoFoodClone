package com.example.gofoodclone.auth.domain

import kotlinx.coroutines.flow.Flow

interface AuthLoader {
}

interface LoginLoader{
    fun login(payloadAuth: PayloadAuth):Flow<BaseDomain<DataAuth?>>
}