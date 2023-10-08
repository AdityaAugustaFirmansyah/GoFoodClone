package com.example.gofoodclone.factories

import com.example.remote.LoginService
import com.example.remote.usecase.RemoteLogin

class RemoteLoginFactory {
    companion object{
        fun createRemoteLoginFactory(loginService: com.example.remote.LoginService): com.example.remote.usecase.RemoteLogin {
            return com.example.remote.usecase.RemoteLogin(loginService)
        }
    }
}