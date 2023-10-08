package com.example.session

import com.example.domain.DataAuth
import com.example.domain.User

data class SessionUser(
    val access_token:String,
    val token_type:String,
    val user: UserLocal?,
)

data class UserLocal(
    val id:Int,
    val name:String,
    val email:String,
    val roles:String,
    val address:String,
    val houseNumber:String,
    val phoneNumber:String,
    val city:String,
    val created_at:Long,
    val updated_at:Long,
    val profile_photo_url:String,
)

fun SessionUser.toDomain(): com.example.domain.DataAuth {
    return com.example.domain.DataAuth(
        access_token, token_type, user?.let {
            com.example.domain.User(
                it.id,
                user.name,
                user.email,
                user.roles,
                user.address,
                user.houseNumber,
                user.phoneNumber,
                user.city,
                user.created_at,
                user.updated_at,
                user.profile_photo_url
            )
        }
    )
}

fun com.example.domain.DataAuth.toLocal(): SessionUser {
    return SessionUser(
        access_token, token_type, user?.let {
            UserLocal(
                it.id,
                it.name,
                it.email,
                it.roles,
                it.address,
                it.houseNumber,
                it.phoneNumber,
                it.city,
                it.created_at,
                it.updated_at,
                it.profile_photo_url
            )
        }
    )
}

