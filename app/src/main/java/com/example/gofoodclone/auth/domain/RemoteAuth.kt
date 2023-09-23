package com.example.gofoodclone.auth.domain

data class RemoteAuth(
    val meta: RemoteMeta,
    val data:DataAuth?
)

data class RemoteMeta(
    val code:Int,
    val status:String,
    val message:String
)