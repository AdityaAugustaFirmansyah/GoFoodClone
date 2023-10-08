package com.example.remote

import com.example.domain.RemoteAuth
import com.example.domain.login.LoginPayload
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    suspend fun login(@Body payloadAuth: LoginPayload): RemoteAuth
}