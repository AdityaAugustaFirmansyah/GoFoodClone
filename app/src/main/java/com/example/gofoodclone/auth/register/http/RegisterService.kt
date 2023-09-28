package com.example.gofoodclone.auth.register.http

import com.example.gofoodclone.auth.domain.RemoteAuth
import com.example.gofoodclone.auth.register.domain.RegisterPayload
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("register")
    suspend fun register(@Body payloadAuth: RegisterPayload): RemoteAuth
}