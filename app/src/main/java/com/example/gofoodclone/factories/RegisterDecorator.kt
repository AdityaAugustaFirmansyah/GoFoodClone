package com.example.gofoodclone.factories

import com.example.domain.BaseDomain
import com.example.domain.DataAuth

import com.example.domain.register.RegisterLoader
import com.example.domain.register.RegisterPayload
import com.example.session.SaveSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterDecorator(val loginLoader: com.example.domain.register.RegisterLoader, val sessionLocal: SaveSession):
    com.example.domain.register.RegisterLoader {
    override fun login(payloadAuth: com.example.domain.register.RegisterPayload): Flow<com.example.domain.BaseDomain<com.example.domain.DataAuth?>> {
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
        fun createFactory(loginLoader: com.example.domain.register.RegisterLoader, sessionLocal: SaveSession): com.example.domain.register.RegisterLoader {
            return RegisterDecorator(loginLoader, sessionLocal)
        }
    }
}