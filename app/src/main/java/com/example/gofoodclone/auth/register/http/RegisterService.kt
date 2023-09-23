package com.example.gofoodclone.auth.register.http

import com.example.gofoodclone.auth.domain.PayloadAuth
import com.example.gofoodclone.auth.domain.RemoteAuth
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("register")
    suspend fun register(@Body payloadAuth: PayloadAuth): RemoteAuth
}