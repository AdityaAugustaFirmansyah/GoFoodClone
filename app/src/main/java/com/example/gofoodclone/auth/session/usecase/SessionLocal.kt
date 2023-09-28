package com.example.gofoodclone.auth.session.usecase

import com.example.gofoodclone.auth.domain.BaseDomain
import com.example.gofoodclone.auth.domain.DataAuth
import com.example.gofoodclone.auth.session.SessionDao
import com.example.gofoodclone.auth.session.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SessionLocal(val tinyDB: SessionDao){
    fun check(): Flow<BaseDomain<DataAuth?>> {
        return flow {
            val data = tinyDB.getSession()
            if (data==null){
                emit(BaseDomain.Failure(Exception("Invalid Token")))
            }else{
                emit(BaseDomain.Success(data.toDomain()))
            }
        }
    }
}