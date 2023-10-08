package com.example.session

import com.example.domain.DataAuth

class SaveSession(val dao: SessionDao) {
    fun save(dataAuth: DataAuth){
        dao.saveSession(dataAuth)
    }
}