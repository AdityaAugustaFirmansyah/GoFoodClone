package com.example.gofoodclone.auth.login.http.usecase

import com.example.gofoodclone.auth.domain.BaseDomain
import com.example.gofoodclone.auth.domain.DataAuth
import com.example.gofoodclone.auth.login.domain.LoginLoader
import com.example.gofoodclone.auth.login.domain.LoginPayload
import com.example.gofoodclone.auth.domain.SaveSession
import com.example.gofoodclone.auth.domain.toDomain
import com.example.gofoodclone.auth.login.http.LoginService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RemoteLogin(val loginService: LoginService,val saveSession: SaveSession) : LoginLoader {
    override fun login(payloadAuth: LoginPayload): Flow<BaseDomain<DataAuth?>> {
        return flow {
            try {
                val data = loginService.login(payloadAuth)
                emit(BaseDomain.Success(data.data?.toDomain()))
                data.data?.let { saveSession.save(it.toDomain()) }
            }catch (t:Throwable){
                when(t) {
                    is IOException -> {
                        emit(BaseDomain.Failure(Connectivity()))
                    }
                    is HttpException -> {
                        emit(BaseDomain.Failure(InvalidData()))
                    }
                    else -> {
                        emit(BaseDomain.Failure(InvalidData()))
                    }
                }
            }
        }
    }
}
class InvalidData : Throwable()
class Connectivity : Throwable()