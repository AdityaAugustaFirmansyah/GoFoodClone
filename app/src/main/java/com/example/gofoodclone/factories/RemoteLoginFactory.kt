package com.example.gofoodclone.factories

import com.example.gofoodclone.auth.login.http.LoginService
import com.example.gofoodclone.auth.login.http.usecase.RemoteLogin

class RemoteLoginFactory {
    companion object{
        fun createRemoteLoginFactory(loginService: LoginService):RemoteLogin{
            return RemoteLogin(loginService)
        }
    }
}