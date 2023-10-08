package com.example.gofoodclone.factories

import com.example.remote.LoginService
import com.example.gofoodclone.framework.HttpFactory

class LoginServiceFactory {
    companion object{
        fun createLoginService(): com.example.remote.LoginService {
            return HttpFactory.createRetrofit().create(com.example.remote.LoginService::class.java)
        }
    }
}