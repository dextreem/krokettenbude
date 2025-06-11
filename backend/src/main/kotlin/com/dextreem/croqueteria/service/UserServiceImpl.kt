package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.entity.User
import com.dextreem.croqueteria.repository.UserRepository
import com.dextreem.croqueteria.request.LoginRequest
import com.dextreem.croqueteria.request.UserRequest
import com.dextreem.croqueteria.response.LoginResponse
import com.dextreem.croqueteria.response.UserResponse
import mu.KLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
    val authenticationManager: AuthenticationManager,
    val passwordEncoder: PasswordEncoder,
    val jwtService: JwtService
) :
    UserService {
    companion object : KLogging()

    @Transactional
    override fun addUser(userRequest: UserRequest): UserResponse {
        if(isEmailTaken(userRequest.email)){
            throw IllegalArgumentException("Email already taken!")
        }

        val user = buildUser(userRequest)
        val savedUser = userRepository.save<User>(user)
        return buildUserResponse(savedUser)
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

    private fun isEmailTaken(email:String): Boolean {
        return userRepository.findByEmail(email).isPresent
    }

    private fun buildUser(userRequest: UserRequest) : User{
        return User(
            id = null,
            email = userRequest.email,
            password = passwordEncoder.encode(userRequest.password),
            role = userRequest.role,
            createdAt = null,
            updatedAt = null
        )

    }

    private fun buildUserResponse(user: User) : UserResponse{
        return UserResponse(
            id = user.id,
            email = user.email,
            role = user.role
        )
    }
}