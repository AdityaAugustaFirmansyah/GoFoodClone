package com.example.gofoodclone.factories


import com.example.session.SaveSession
import com.example.session.SessionDao

class SaveSessionFactory {
    companion object{
        fun createSaveSession(dao: com.example.session.SessionDao): SaveSession {
            return SaveSession(dao)
        }
    }
}