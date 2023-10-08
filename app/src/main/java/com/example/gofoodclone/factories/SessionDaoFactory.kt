package com.example.gofoodclone.factories

import com.example.gofoodclone.auth.session.SessionDao
import com.example.gofoodclone.framework.TinyDB

class SessionDaoFactory {
    companion object{
        fun createSessionDaoFactory(tinyDB: TinyDB):SessionDao{
            return SessionDao(tinyDB)
        }
    }
}