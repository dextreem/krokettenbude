package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.repository.CroquetteRepository
import com.dextreem.croqueteria.repository.RatingRepository
import com.dextreem.croqueteria.repository.UserRepository
import com.dextreem.croqueteria.request.UserRequest
import com.dextreem.croqueteria.response.UserResponse
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {
    companion object : KLogging()

    fun addUser(userRequest: UserRequest) {
        throw Exception("not yet implemented")
    }

    fun retrieveAllUsers(userRole: String?): List<UserResponse> {
        throw Exception("not yet implemented")
    }

    fun retrieveUserById(userId: Int?): List<UserResponse> {
        throw Exception("not yet implemented")
    }

    fun updateUser(userId: Int, userRequest: UserRequest) {
        throw Exception("not yet implemented")
    }

    fun deleteUser(userId: Int) {
        throw Exception("not yet implemented")
    }
}