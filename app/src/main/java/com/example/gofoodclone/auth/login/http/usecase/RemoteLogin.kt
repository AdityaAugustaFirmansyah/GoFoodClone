package com.example.gofoodclone.auth.login.http.usecase

import com.example.gofoodclone.auth.domain.BaseDomain
import com.example.gofoodclone.auth.domain.DataAuth
import com.example.gofoodclone.auth.domain.LoginLoader
import com.example.gofoodclone.auth.domain.PayloadAuth
import com.example.gofoodclone.auth.domain.SaveSession
import com.example.gofoodclone.auth.login.http.LoginService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteLogin(val loginService: LoginService,val saveSession: SaveSession) : LoginLoader {
    override fun login(payloadAuth: PayloadAuth): Flow<BaseDomain<DataAuth?>> {
        return flow {
            val data = loginService.login(payloadAuth)
            if (data.data==null){
                emit(BaseDomain.Failure(InvalidData()))
            }else{
                emit(BaseDomain.Success(data.data))
                saveSession.save(data.data)
            }
        }
    }
}
class InvalidData : Throwable()
class Connectivity : Throwable()