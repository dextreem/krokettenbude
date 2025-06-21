package com.dextreem.croqueteria.controller

import com.dextreem.croqueteria.request.LoginRequest
import com.dextreem.croqueteria.request.UserCreateRequest
import com.dextreem.croqueteria.request.UserUpdateRequest
import com.dextreem.croqueteria.response.ApiErrorResponse
import com.dextreem.croqueteria.response.LoginResponse
import com.dextreem.croqueteria.response.UserResponse
import com.dextreem.croqueteria.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
@Validated
@Tag(name = "User Rest API Endpoints", description = "Operations related to users.")
class UserController(val userService: UserService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Create a new user",
        description = "Creates a new user and assigns the respective role."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "User created successfully",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = UserResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid input or email already taken",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiErrorResponse::class))]
            )
        ]
    )
    @SecurityRequirements
    fun addUser(@RequestBody @Valid userCreateRequest: UserCreateRequest): UserResponse {
        return userService.addUser(userCreateRequest)
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Login user",
        description = "Logs in a user and returns a JWT upon success."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Login successful",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = LoginResponse::class))]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Invalid credentials",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiErrorResponse::class))]
            )
        ]
    )
    @SecurityRequirements
    fun login(@RequestBody @Valid loginRequest: LoginRequest): LoginResponse {
        return userService.login(loginRequest)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get all users",
        description = "Retrieves all users, optionally filtered by user role."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "List of users",
                content = [Content(mediaType = "application/json", array = ArraySchema(schema = Schema(implementation = UserResponse::class)))]
            )
        ]
    )
    fun retrieveAllUsers(
        @Parameter(description = "Optional filter by user role")
        @RequestParam("user_role", required = false) userRole: String?
    ): List<UserResponse> {
        return userService.retrieveAllUsers(userRole)
    }

    @GetMapping("/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get a single user",
        description = "Retrieves a single user by their ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "User found",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = UserResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Forbidden to retrieve other user's profile",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "User not found",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiErrorResponse::class))]
            )
        ]
    )
    fun retrieveUserById(
        @Parameter(description = "ID of the user to retrieve")
        @PathVariable("user_id") userId: Int
    ): UserResponse {
        return userService.retrieveUserById(userId)
    }

    @PutMapping("/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Update a user",
        description = "Updates a user identified by their ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "User updated",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = UserResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Forbidden to update other user's profile",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "User not found",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiErrorResponse::class))]
            )
        ]
    )
    fun updateUser(
        @Parameter(description = "ID of the user to update")
        @PathVariable("user_id") userId: Int,
        @RequestBody userUpdateRequest: UserUpdateRequest
    ): UserResponse {
        return userService.updateUser(userId, userUpdateRequest)
    }

    @DeleteMapping("/{user_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete a user",
        description = "Deletes a user identified by their ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "User deleted"
            ),
            ApiResponse(
                responseCode = "403",
                description = "Forbidden to delete other user's profile as non-MANAGER",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "User not found",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiErrorResponse::class))]
            )
        ]
    )
    fun deleteUser(
        @Parameter(description = "ID of the user to delete")
        @PathVariable("user_id") userId: Int
    ) {
        userService.deleteUser(userId)
    }
}
