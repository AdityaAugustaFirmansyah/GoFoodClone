package com.example.gofoodclone.factories

import com.example.session.SessionDao
import com.example.session.TinyDB

class SessionDaoFactory {
    companion object{
        fun createSessionDaoFactory(tinyDB: com.example.session.TinyDB): com.example.session.SessionDao {
            return com.example.session.SessionDao(tinyDB)
        }
    }
}