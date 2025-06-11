package com.dextreem.croqueteria.controller

import com.dextreem.croqueteria.request.LoginRequest
import com.dextreem.croqueteria.request.UserRequest
import com.dextreem.croqueteria.response.LoginResponse
import com.dextreem.croqueteria.response.UserResponse
import com.dextreem.croqueteria.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
@Validated
@Tag(name = "User Rest API Endpoints", description = "Operations related to comments.")
class UserController(val userService: UserService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Create a new user",
        description = "Creates a new user and assigns the respective role."
    )
    @SecurityRequirements
    fun addUser(@RequestBody @Valid userRequest: UserRequest): UserResponse {
        return userService.addUser(userRequest)
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Create a new user",
        description = "Creates a new user and assigns the respective role."
    )
    @SecurityRequirements
    fun login(@RequestBody @Valid loginRequest: LoginRequest): LoginResponse {
        return userService.login(loginRequest)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get all users",
        description = "Retrieves a single user by its ID"
    )
    fun retrieveAllUsers(@RequestParam("user_role") userRole: String?): List<UserResponse> {
        return userService.retrieveAllUsers(userRole)
    }

    @GetMapping("/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get a single rating",
        description = "Retrieves a single comment by its ID"
    )
    fun retrieveUserById(
        @PathVariable("user_id") userId: Int?,
    ): List<UserResponse> {
        return userService.retrieveUserById(userId)
    }

    @PutMapping("/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Update a single user",
        description = "Updates a single user by its ID"
    )
    fun updateUser(
        @PathVariable("user_id") userId: Int,
        @RequestBody userRequest: UserRequest
    ) {
        return userService.updateUser(userId, userRequest)
    }

    @Operation(
        summary = "Delete a single user",
        description = "Deletes a single user by its ID"
    )
    @DeleteMapping("/{user_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(
        @PathVariable("user_id") userId: Int,
    ) {
        userService.deleteUser(userId)
    }

}

