package com.example.session

class SessionDao(val tinyDB: TinyDB) {
    fun getSession(): SessionUser? {
        return tinyDB.getObject("user", SessionUser::class.java)
    }

    fun saveSession(dataAuth: com.example.domain.DataAuth) {
        tinyDB.putObject("user",dataAuth.toLocal())
    }

}