package com.example.gofoodclone.auth.local.usecase

import com.example.gofoodclone.auth.domain.BaseDomain
import com.example.gofoodclone.auth.domain.DataAuth
import com.example.gofoodclone.auth.domain.LoginLoader
import com.example.gofoodclone.auth.domain.PayloadAuth
import com.example.gofoodclone.framework.TinyDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalLogin(val tinyDB: TinyDB){
    fun check(): Flow<BaseDomain<DataAuth?>> {
        return flow {
            val data = tinyDB.getObject("user",DataAuth::class.java)
            if (data==null){
                emit(BaseDomain.Failure(Exception("Invalid Token")))
            }else{
                emit(BaseDomain.Success(data))
            }
        }
    }
}