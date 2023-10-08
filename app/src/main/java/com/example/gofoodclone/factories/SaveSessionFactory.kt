package com.example.gofoodclone.factories

import com.example.gofoodclone.auth.domain.SaveSession
import com.example.gofoodclone.auth.session.SessionDao

class SaveSessionFactory {
    companion object{
        fun createSaveSession(dao: SessionDao):SaveSession{
            return SaveSession(dao)
        }
    }
}