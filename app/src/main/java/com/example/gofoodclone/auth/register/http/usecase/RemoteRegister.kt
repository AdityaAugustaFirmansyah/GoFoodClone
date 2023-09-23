package com.example.gofoodclone.auth.register.http.usecase

import com.example.gofoodclone.auth.domain.BaseDomain
import com.example.gofoodclone.auth.domain.DataAuth
import com.example.gofoodclone.auth.domain.LoginLoader
import com.example.gofoodclone.auth.domain.PayloadAuth
import com.example.gofoodclone.auth.domain.SaveSession
import com.example.gofoodclone.auth.login.http.usecase.InvalidData
import com.example.gofoodclone.auth.register.http.RegisterService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteRegister(val registerService: RegisterService, val saveSession: SaveSession) : LoginLoader {
    override fun login(payloadAuth: PayloadAuth): Flow<BaseDomain<DataAuth?>> {
        return flow {
            val data = registerService.register(payloadAuth)
            if (data.data==null){
                emit(BaseDomain.Failure(InvalidData()))
            }else{
                emit(BaseDomain.Success(data.data))
                saveSession.save(data.data)
            }
        }
    }
}
