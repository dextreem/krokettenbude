package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.entity.User
import com.dextreem.croqueteria.entity.UserRole
import com.dextreem.croqueteria.exception.AccessForbiddenException
import com.dextreem.croqueteria.exception.ResourceNotFoundException
import com.dextreem.croqueteria.repository.UserRepository
import com.dextreem.croqueteria.request.LoginRequest
import com.dextreem.croqueteria.request.UserCreateRequest
import com.dextreem.croqueteria.request.UserUpdateRequest
import com.dextreem.croqueteria.response.LoginResponse
import com.dextreem.croqueteria.response.UserResponse
import com.dextreem.croqueteria.util.FindAuthenticatedUser
import com.dextreem.croqueteria.util.InputSanitizer
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
    override fun addUser(userCreateRequest: UserCreateRequest): UserResponse {
        logger.info("Try to create new user ${userCreateRequest.email}.")
        if (isEmailTaken(userCreateRequest.email)) {
            throw IllegalArgumentException("Email already taken!")
        }

        val user = buildNewUser(userCreateRequest)
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
        return LoginResponse(
            token = token,
            id = user.id,
            email = user.username,
            role = user.role.name
        )
    }

    @Transactional(readOnly = true)
    override fun retrieveAllUsers(userRole: String?): List<UserResponse> {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User ${actorUser.username} requested all users. Role: $userRole")
        if (userRole != null) {
            return userRepository.findByRole(userRole).map { buildUserResponse((it)) }
        }
        logger.info("Retrieved all users. Returning.")
        return userRepository.findAll().map { buildUserResponse(it) }
    }

    @Transactional(readOnly = true)
    override fun retrieveUserById(userId: Int): UserResponse {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User ${actorUser.username} requested user with ID $userId")
        val user = retrieveExistingUserById(userId)
        if (actorUser.id != userId && actorUser.role != UserRole.MANAGER) {
            throw AccessForbiddenException("Not allowed to retrieve other user profiles as a non-manager!")
        }
        logger.info("Retrieved user $userId. Returning")
        return buildUserResponse(user)
    }

    @Transactional
    override fun updateUser(userId: Int, userUpdateRequest: UserUpdateRequest): UserResponse {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User ${actorUser.username} requested to update user with ID $userId")
        if (actorUser.id != userId && actorUser.role != UserRole.MANAGER) {
            throw AccessForbiddenException("Not allowed to modify other user profiles as a non-manager!")
        }
        val user = mergeToUserIfExist(userId, userUpdateRequest)
        val updatedUser = userRepository.save(user)
        logger.info("User $userId successfully added.")
        return buildUserResponse(updatedUser)
    }

    @Transactional
    override fun deleteUser(userId: Int) {
        val actorUser: User = findAuthenticatedUser.getAuthenticatedUser()
        logger.info("User ${actorUser.username} tries to delete user with ID $userId")
        if (actorUser.id != userId && actorUser.role != UserRole.MANAGER) {
            throw AccessForbiddenException("Not allowed to delete other user profiles as a non-manager!")
        }
        val user: User = retrieveExistingUserById(userId)
        userRepository.delete(user)
        logger.info("User ${user.username} (ID: $userId) deleted by user ${actorUser.username}")
    }

    private fun isEmailTaken(email: String): Boolean {
        return userRepository.findByEmail(email).isPresent
    }

    private fun buildNewUser(userCreateRequest: UserCreateRequest): User {
        return User(
            id = null,
            email = InputSanitizer.sanitize(userCreateRequest.email),
            password = passwordEncoder.encode(userCreateRequest.password),
            role = userCreateRequest.role,
            createdAt = null,
            updatedAt = null
        )
    }

    private fun mergeToUserIfExist(userId: Int, userUpdateRequest: UserUpdateRequest): User {
        val user = userRepository.findById(userId).orElseThrow {
            ResourceNotFoundException("User with ID $userId not found!")
        }

        userUpdateRequest.password?.let { user.setPassword(passwordEncoder.encode(it)) }
        userUpdateRequest.role?.let { user.role = it }
        return user
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