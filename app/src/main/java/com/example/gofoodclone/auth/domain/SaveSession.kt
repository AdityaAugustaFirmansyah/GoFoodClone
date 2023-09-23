package com.example.gofoodclone.auth.domain

import com.example.gofoodclone.auth.domain.DataAuth
import com.example.gofoodclone.framework.TinyDB

class SaveSession(private val tinyDB: TinyDB) {
    fun save(dataAuth: DataAuth){
        tinyDB.putObject("user",dataAuth)
    }
}