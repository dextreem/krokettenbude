package com.dextreem.croqueteria.controller

import com.dextreem.croqueteria.request.UserRequest
import com.dextreem.croqueteria.response.UserResponse
import com.dextreem.croqueteria.service.UserService
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
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/users")
@Validated
class UserController(val userService: UserService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addUser(@RequestBody @Valid userRequest: UserRequest) {
        return userService.addUser(userRequest)
    }

    @GetMapping("/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllUsers(
        @PathVariable("user_id") userId: Int?,
    ): List<UserResponse> {
        return userService.retrieveAllUsers(userId)
    }

    @PutMapping("/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateUser(
        @PathVariable("user_id") userId: Int,
        @RequestBody userRequest: UserRequest
    ) {
        return userService.updateUser(userId, userRequest)
    }

    @DeleteMapping("/{user_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCroquette(
        @PathVariable("user_id") userId: Int,
    ) {
        userService.deleteCroquette(userId)
    }

}

