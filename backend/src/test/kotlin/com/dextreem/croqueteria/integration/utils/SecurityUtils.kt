package com.dextreem.croqueteria.integration.utils

import com.dextreem.croqueteria.entity.User
import com.dextreem.croqueteria.service.JwtService

fun createAuthToken(user: User, jwtService: JwtService): String = jwtService.generateToken(mapOf(), user)