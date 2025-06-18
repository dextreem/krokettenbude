package com.dextreem.croqueteria.response

data class LoginResponse(
    val id: Int?,
    val token: String,
    val email: String,
    val role: String
)