package com.example.gofoodclone.factories

import com.example.gofoodclone.auth.domain.BaseDomain
import com.example.gofoodclone.auth.domain.DataAuth
import com.example.gofoodclone.auth.domain.SaveSession
import com.example.gofoodclone.auth.domain.toDomain
import com.example.gofoodclone.auth.login.domain.LoginLoader
import com.example.gofoodclone.auth.login.domain.LoginPayload
import com.example.gofoodclone.auth.register.domain.RegisterLoader
import com.example.gofoodclone.auth.register.domain.RegisterPayload
import com.example.gofoodclone.auth.session.usecase.SessionLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class RegisterDecorator(val loginLoader: RegisterLoader, val sessionLocal: SaveSession):RegisterLoader{
    override fun login(payloadAuth: RegisterPayload): Flow<BaseDomain<DataAuth?>> {
        return flow {
            loginLoader.login(payloadAuth).collect{ it ->
                if (it is BaseDomain.Success){
                    val  data = it.data
                    data?.let { sessionLocal.save(it) }
                }
            }
        }
    }
    companion object{
        fun createFactory(loginLoader: RegisterLoader,sessionLocal: SaveSession):RegisterLoader{
            return RegisterDecorator(loginLoader, sessionLocal)
        }
    }
}