package com.example.gofoodclone.factories

import com.example.gofoodclone.auth.login.http.LoginService
import com.example.gofoodclone.auth.login.http.usecase.RemoteLogin
import com.example.gofoodclone.auth.register.domain.RegisterLoader
import com.example.gofoodclone.auth.register.http.RegisterService
import com.example.gofoodclone.auth.register.http.usecase.RemoteRegister

class RemoteRegisterFactory {
    companion object{
        fun createRemoteLoginFactory(loginService: RegisterService):RegisterLoader{
            return RemoteRegister(loginService)
        }
    }
}