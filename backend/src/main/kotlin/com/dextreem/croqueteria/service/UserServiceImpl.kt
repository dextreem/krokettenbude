package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.entity.User
import com.dextreem.croqueteria.entity.UserRole
import com.dextreem.croqueteria.exception.ResourceNotFoundException
import com.dextreem.croqueteria.repository.UserRepository
import com.dextreem.croqueteria.request.LoginRequest
import com.dextreem.croqueteria.request.UserRequest
import com.dextreem.croqueteria.response.LoginResponse
import com.dextreem.croqueteria.response.UserResponse
import com.dextreem.croqueteria.util.FindAuthenticatedUser
import mu.KLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional
import java.util.function.Supplier


@Service
class UserServiceImpl(
    val userRepository: UserRepository,
    val authenticationManager: AuthenticationManager,
    val passwordEncoder: PasswordEncoder,
    val jwtService: JwtService,
    val findAuthenticatedUser: FindAuthenticatedUser
) :
    UserService {
    companion object : KLogging()

    @Transactional
    override fun addUser(userRequest: UserRequest): UserResponse {
        logger.info("Try to create new user ${userRequest.email}.")
        if (isEmailTaken(userRequest.email!!)) {
            throw IllegalArgumentException("Email already taken!")
        }

        val user = buildUser(userRequest)
        val savedUser = userRepository.save<User>(user)

        logger.info("User ${savedUser.username} successfully created.")
        return buildUserResponse(savedUser)
    }

    @Transactional(readOnly = true)
    override fun login(loginRequest: LoginRequest): LoginResponse {
        logger.info("User ${loginRequest.email} tries to login.")
        authenticationManager.authenticate(
            (UsernamePasswordAuthenticationToken(
                loginRequest.email,
                loginRequest.password
            ))
        )

        val user = userRepository.findByEmail(loginRequest.email)
            .orElseThrow(Supplier { IllegalArgumentException("Invalid credentials!") })
        val token = jwtService.generateToken(mapOf(), user)

        logger.info("User ${user.username} successfully logged in.")
        return LoginResponse(token = token)
    }

    @Transactional(readOnly = true)
    override fun retrieveAllUsers(userRole: String?): List<UserResponse> {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User ${actorUser.username} requested all users")
        return userRepository.findAll().map { buildUserResponse(it) }
    }

    @Transactional(readOnly = true)
    override fun retrieveUserById(userId: Int): UserResponse {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User ${actorUser.username} requested user with ID $userId")
        val user: Optional<User> = userRepository.findById(userId)
        if (!user.isPresent) {
            throw ResourceNotFoundException("User with ID $userId not found!")
        }
        return buildUserResponse(user.get())
    }

    @Transactional
    override fun updateUser(userId: Int, userRequest: UserRequest): UserResponse {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User ${actorUser.username} requested to update user with ID $userId")

        val user = userRepository.findById(userId).orElseThrow {
            ResourceNotFoundException("User with ID $userId not found!")
        }

        userRequest.email?.let {
            if (it != user.getUsername() && isEmailTaken(it)) {
                throw IllegalArgumentException("Email already taken!")
            }
            user.setEmail(it)
        }

        userRequest.password?.let {
            user.setPassword(passwordEncoder.encode(it))
        }

        userRequest.role?.let {
            val roleEnum = UserRole.fromString(it)
                ?: throw IllegalArgumentException("Invalid user role: $it")
            user.role = roleEnum
        }

        val updatedUser = userRepository.save(user)
        return buildUserResponse(updatedUser)
    }

    @Transactional
    override fun deleteUser(userId: Int) {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User ${actorUser.username} tries to delete user with ID $userId")
        val user: User = retrieveExistingUserById(userId)
        userRepository.delete(user)
        logger.info("User ${user.username} (ID: $userId) deleted by user ${actorUser.username}")
    }

    private fun isEmailTaken(email: String): Boolean {
        return userRepository.findByEmail(email).isPresent
    }

    private fun buildUser(userRequest: UserRequest): User {
        return User(
            id = null,
            email = userRequest.email!!,
            password = passwordEncoder.encode(userRequest.password!!),
            role = UserRole.fromString(userRequest.role!!)
                ?: throw IllegalArgumentException("Invalid user role ${userRequest.role}"),
            createdAt = null,
            updatedAt = null
        )
    }

    private fun buildUserResponse(user: User): UserResponse {
        return UserResponse(
            id = user.id,
            email = user.username,
            role = user.role.authority
        )
    }

    private fun retrieveExistingUserById(userId: Int): User {
        val user: Optional<User> = userRepository.findById(userId)
        if (!user.isPresent) {
            throw ResourceNotFoundException("No user found with ID $userId")
        }
        return user.get()
    }
}