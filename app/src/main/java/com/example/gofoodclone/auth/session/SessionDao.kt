package com.example.gofoodclone.auth.session

import com.example.gofoodclone.auth.domain.DataAuth
import com.example.gofoodclone.auth.session.usecase.SessionLocal
import com.example.gofoodclone.framework.TinyDB

class SessionDao(val tinyDB: TinyDB) {
    fun getSession(): SessionUser? {
        return tinyDB.getObject("user",SessionUser::class.java)
    }

    fun saveSession(dataAuth: DataAuth) {
        tinyDB.putObject("user",dataAuth.toLocal())
    }

}