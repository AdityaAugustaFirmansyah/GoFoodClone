package com.example.gofoodclone.auth.register.http.usecase

import com.example.gofoodclone.auth.domain.BaseDomain
import com.example.gofoodclone.auth.domain.DataAuth
import com.example.gofoodclone.auth.domain.SaveSession
import com.example.gofoodclone.auth.domain.toDomain
import com.example.gofoodclone.auth.login.http.usecase.Connectivity
import com.example.gofoodclone.auth.login.http.usecase.InvalidData
import com.example.gofoodclone.auth.register.domain.RegisterLoader
import com.example.gofoodclone.auth.register.domain.RegisterPayload
import com.example.gofoodclone.auth.register.http.RegisterService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RemoteRegister(val registerService: RegisterService) :
    RegisterLoader {
    override fun login(payloadAuth: RegisterPayload): Flow<BaseDomain<DataAuth?>> {
        return flow {
            try {
                val data = registerService.register(payloadAuth)
                emit(BaseDomain.Success(data.data?.toDomain()))
            } catch (t: Throwable) {
                when (t) {
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
