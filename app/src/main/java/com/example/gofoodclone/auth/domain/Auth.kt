package com.example.gofoodclone.auth.domain


data class DataAuth(
    val access_token:String,
    val token_type:String,
    val user:User?,
)

data class User(
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

data class PayloadAuth(
    val email:String,
    val password:String,
    val name:String?=null,
    val password_confirmation:String?=null,
    val address:String?=null,
    val city:String?=null,
    val houseNumber:String?=null,
    val phoneNumber:String?=null
)
