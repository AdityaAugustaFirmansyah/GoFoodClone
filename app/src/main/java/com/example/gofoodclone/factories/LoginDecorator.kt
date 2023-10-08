package com.example.gofoodclone.factories

import com.example.gofoodclone.auth.domain.BaseDomain
import com.example.gofoodclone.auth.domain.DataAuth
import com.example.gofoodclone.auth.domain.SaveSession
import com.example.gofoodclone.auth.domain.toDomain
import com.example.gofoodclone.auth.login.domain.LoginLoader
import com.example.gofoodclone.auth.login.domain.LoginPayload
import com.example.gofoodclone.auth.session.usecase.SessionLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class LoginDecorator(val loginLoader: LoginLoader,val sessionLocal: SaveSession):LoginLoader{
    override fun login(payloadAuth: LoginPayload): Flow<BaseDomain<DataAuth?>> {
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
        fun createFactory(loginLoader: LoginLoader,sessionLocal: SaveSession):LoginDecorator{
            return LoginDecorator(loginLoader, sessionLocal)
        }
    }
}