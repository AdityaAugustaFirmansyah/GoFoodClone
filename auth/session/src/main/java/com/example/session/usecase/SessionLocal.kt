package com.example.session.usecase

import com.example.domain.BaseDomain
import com.example.domain.DataAuth
import com.example.session.SessionDao
import com.example.session.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SessionLocal(val tinyDB: SessionDao){
    fun check(): Flow<com.example.domain.BaseDomain<com.example.domain.DataAuth?>> {
        return flow {
            val data = tinyDB.getSession()
            if (data==null){
                emit(com.example.domain.BaseDomain.Failure(Exception("Invalid Token")))
            }else{
                emit(com.example.domain.BaseDomain.Success(data.toDomain()))
            }
        }
    }
}