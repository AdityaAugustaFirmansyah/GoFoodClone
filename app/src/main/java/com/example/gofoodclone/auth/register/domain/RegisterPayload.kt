package com.example.gofoodclone.auth.register.domain

data class RegisterPayload(
    val email:String,
    val password:String,
    val name:String?=null,
    val password_confirmation:String?=null,
    val address:String?=null,
    val city:String?=null,
    val houseNumber:String?=null,
    val phoneNumber:String?=null
)