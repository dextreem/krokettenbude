package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.request.LoginRequest
import com.dextreem.croqueteria.request.UserCreateRequest
import com.dextreem.croqueteria.request.UserUpdateRequest
import com.dextreem.croqueteria.response.LoginResponse
import com.dextreem.croqueteria.response.UserResponse

interface UserService {
    fun addUser(userCreateRequest: UserCreateRequest): UserResponse
    fun login(loginRequest: LoginRequest): LoginResponse
    fun retrieveAllUsers(userRole: String?): List<UserResponse>
    fun retrieveUserById(userId: Int): UserResponse
    fun updateUser(userId: Int, userUpdateRequest: UserUpdateRequest): UserResponse
    fun deleteUser(userId: Int)
}