package com.example.gofoodclone.factories

import com.example.remote.RegisterService
import com.example.gofoodclone.framework.HttpFactory

class RegisterServiceFactory {
    companion object{
        fun createLoginService(): com.example.remote.RegisterService {
            return HttpFactory.createRetrofit().create(com.example.remote.RegisterService::class.java)
        }
    }
}