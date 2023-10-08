package com.example.gofoodclone.factories

import com.example.session.SaveSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginDecorator(val loginLoader: com.example.domain.login.LoginLoader, val sessionLocal: SaveSession):
    com.example.domain.login.LoginLoader {
    override fun login(payloadAuth: com.example.domain.login.LoginPayload): Flow<com.example.domain.BaseDomain<com.example.domain.DataAuth?>> {
        return flow {
            loginLoader.login(payloadAuth).collect{ it ->
                if (it is com.example.domain.BaseDomain.Success){
                    val  data = it.data
                    data?.let { sessionLocal.save(it) }
                }
            }
        }
    }
    companion object{
        fun createFactory(loginLoader: com.example.domain.login.LoginLoader, sessionLocal: SaveSession):LoginDecorator{
            return LoginDecorator(loginLoader, sessionLocal)
        }
    }
}