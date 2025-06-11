package com.dextreem.croqueteria.util

import com.dextreem.croqueteria.entity.User

interface FindAuthenticatedUser {
    fun getAuthenticatedUser() : User
}