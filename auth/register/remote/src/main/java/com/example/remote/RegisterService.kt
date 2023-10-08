package com.example.remote

import com.example.domain.RemoteAuth
import com.example.domain.register.RegisterPayload
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("register")
    suspend fun register(@Body payloadAuth: com.example.domain.register.RegisterPayload): com.example.domain.RemoteAuth
}