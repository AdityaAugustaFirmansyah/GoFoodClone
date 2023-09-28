package com.example.gofoodclone.auth.domain

import com.example.gofoodclone.auth.domain.DataAuth
import com.example.gofoodclone.auth.session.SessionDao
import com.example.gofoodclone.framework.TinyDB

class SaveSession(val dao: SessionDao) {
    fun save(dataAuth: DataAuth){
        dao.saveSession(dataAuth)
    }
}