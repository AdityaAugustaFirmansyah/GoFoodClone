package com.example.remote.usecase

import com.example.domain.BaseDomain
import com.example.domain.Connectivity
import com.example.domain.DataAuth
import com.example.domain.InvalidData
import com.example.domain.login.LoginLoader
import com.example.domain.login.LoginPayload
import com.example.domain.toDomain
import com.example.remote.LoginService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RemoteLogin(val loginService: LoginService) : LoginLoader {
    override fun login(payloadAuth: LoginPayload): Flow<BaseDomain<DataAuth?>> {
        return flow {
            try {
                val data = loginService.login(payloadAuth)
                emit(BaseDomain.Success(data.data?.toDomain()))
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