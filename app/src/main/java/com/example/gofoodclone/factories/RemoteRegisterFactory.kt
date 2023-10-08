package com.example.gofoodclone.factories

import com.example.domain.register.RegisterLoader
import com.example.remote.RegisterService
import com.example.remote.usecase.RemoteRegister

class RemoteRegisterFactory {
    companion object{
        fun createRemoteLoginFactory(loginService: com.example.remote.RegisterService): com.example.domain.register.RegisterLoader {
            return com.example.remote.usecase.RemoteRegister(loginService)
        }
    }
}