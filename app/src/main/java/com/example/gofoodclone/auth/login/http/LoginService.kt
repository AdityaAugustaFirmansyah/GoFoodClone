package com.example.gofoodclone.auth.login.http

import com.example.gofoodclone.auth.domain.PayloadAuth
import com.example.gofoodclone.auth.domain.RemoteAuth
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    suspend fun login(@Body payloadAuth: PayloadAuth): RemoteAuth
}