package com.example.remote.usecase

import com.example.domain.BaseDomain
import com.example.domain.Connectivity
import com.example.domain.DataAuth
import com.example.domain.InvalidData
import com.example.domain.toDomain
import com.example.domain.register.RegisterLoader
import com.example.domain.register.RegisterPayload
import com.example.remote.RegisterService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RemoteRegister(val registerService: RegisterService) :
    com.example.domain.register.RegisterLoader {
    override fun login(payloadAuth: com.example.domain.register.RegisterPayload): Flow<com.example.domain.BaseDomain<com.example.domain.DataAuth?>> {
        return flow {
            try {
                val data = registerService.register(payloadAuth)
                emit(com.example.domain.BaseDomain.Success(data.data?.toDomain()))
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> {
                        emit(com.example.domain.BaseDomain.Failure(Connectivity()))
                    }

                    is HttpException -> {
                        emit(com.example.domain.BaseDomain.Failure(InvalidData()))
                    }

                    else -> {
                        emit(com.example.domain.BaseDomain.Failure(InvalidData()))
                    }
                }
            }
        }
    }
}
