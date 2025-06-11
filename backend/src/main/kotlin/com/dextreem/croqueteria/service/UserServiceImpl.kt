package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.repository.UserRepository
import com.dextreem.croqueteria.request.LoginRequest
import com.dextreem.croqueteria.request.UserRequest
import com.dextreem.croqueteria.response.LoginResponse
import com.dextreem.croqueteria.response.UserResponse
import mu.KLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
    val authenticationManager: AuthenticationManager,
    val jwtService: JwtService
) :
    UserService {
    companion object : KLogging()

    @Transactional
    override fun addUser(userRequest: UserRequest): UserResponse {
        throw Exception("not yet implemented")
    }

    @Transactional(readOnly = true)
    override fun login(loginRequest: LoginRequest): LoginResponse {
        throw Exception("not yet implemented")
    }

    @Transactional(readOnly = true)
    override fun retrieveAllUsers(userRole: String?): List<UserResponse> {
        throw Exception("not yet implemented")
    }

    @Transactional(readOnly = true)
    override fun retrieveUserById(userId: Int?): List<UserResponse> {
        throw Exception("not yet implemented")
    }

    @Transactional
    override fun updateUser(userId: Int, userRequest: UserRequest) {
        throw Exception("not yet implemented")
    }

    @Transactional
    override fun deleteUser(userId: Int) {
        throw Exception("not yet implemented")
    }
}