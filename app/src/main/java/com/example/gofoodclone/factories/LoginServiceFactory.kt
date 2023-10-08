package com.example.gofoodclone.factories

import com.example.gofoodclone.auth.login.http.LoginService
import com.example.gofoodclone.framework.HttpFactory

class LoginServiceFactory {
    companion object{
        fun createLoginService():LoginService{
            return HttpFactory.createRetrofit().create(LoginService::class.java)
        }
    }
}