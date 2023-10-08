package com.example.gofoodclone.factories

import com.example.gofoodclone.auth.login.http.LoginService
import com.example.gofoodclone.auth.register.http.RegisterService
import com.example.gofoodclone.framework.HttpFactory

class RegisterServiceFactory {
    companion object{
        fun createLoginService():RegisterService{
            return HttpFactory.createRetrofit().create(RegisterService::class.java)
        }
    }
}