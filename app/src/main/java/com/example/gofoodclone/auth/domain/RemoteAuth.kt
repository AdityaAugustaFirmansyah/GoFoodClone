package com.example.gofoodclone.auth.domain

data class RemoteAuth(
    val meta: RemoteMeta,
    val data: DataAuthRemote?
)

data class DataAuthRemote(
    val access_token: String,
    val token_type: String,
    val user: UserRemote?,
)

data class UserRemote(
    val id: Int,
    val name: String,
    val email: String,
    val roles: String,
    val address: String,
    val houseNumber: String,
    val phoneNumber: String,
    val city: String,
    val created_at: Long,
    val updated_at: Long,
    val profile_photo_url: String,
)

data class RemoteMeta(
    val code: Int,
    val status: String,
    val message: String
)

fun DataAuthRemote.toDomain(): DataAuth {
    return DataAuth(
        access_token, token_type, user?.let {
            User(
                user.id,
                user.name,
                user.email,
                user.roles,
                user.address,
                user.houseNumber,
                user.phoneNumber,
                user.city,
                user.created_at,
                user.updated_at,
                it.profile_photo_url
            )
        }
    )
}