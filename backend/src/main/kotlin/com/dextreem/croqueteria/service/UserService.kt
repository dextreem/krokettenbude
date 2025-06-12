package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.request.LoginRequest
import com.dextreem.croqueteria.request.UserRequest
import com.dextreem.croqueteria.response.LoginResponse
import com.dextreem.croqueteria.response.UserResponse

interface UserService {
    fun addUser(userRequest: UserRequest): UserResponse
    fun login(loginRequest: LoginRequest): LoginResponse
    fun retrieveAllUsers(userRole: String?): List<UserResponse>
    fun retrieveUserById(userId: Int): UserResponse
    fun updateUser(userId: Int, userRequest: UserRequest): UserResponse
    fun deleteUser(userId: Int)
}