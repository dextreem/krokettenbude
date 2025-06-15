package com.dextreem.croqueteria.integration.controller

import com.dextreem.croqueteria.entity.User
import com.dextreem.croqueteria.entity.UserRole
import com.dextreem.croqueteria.integration.utils.createAuthToken
import com.dextreem.croqueteria.integration.utils.userEntityList
import com.dextreem.croqueteria.repository.CroquetteRepository
import com.dextreem.croqueteria.repository.UserRepository
import com.dextreem.croqueteria.request.UserUpdateRequest
import com.dextreem.croqueteria.request.UserCreateRequest
import com.dextreem.croqueteria.response.UserResponse
import com.dextreem.croqueteria.service.JwtService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@Tag("integration")
class UserControllerIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var jwtService: JwtService

    val endpoint = "/api/v1/users"

    lateinit var savedUsers: List<User>

    @BeforeEach
    fun setup() {
        userRepository.deleteAll()

        savedUsers = userRepository.saveAll(userEntityList()).toList()
    }

    @AfterEach
    fun clearDBs(){
        userRepository.deleteAll()
    }

    @Test
    fun addUser() {
        val userCreateRequest = UserCreateRequest(
            email = "some@user.com",
            password = "somepassword",
            role = UserRole.USER.name
        )

        val savedUser = webTestClient
            .post()
            .uri(endpoint)
            .bodyValue(userCreateRequest)
            .exchange()
            .expectStatus().isCreated
            .expectBody(UserResponse::class.java)
            .returnResult()
            .responseBody

        assertEquals(savedUsers.size + 1, userRepository.findAll().map { it }.size)
        assertTrue(savedUser?.id != null)
    }

    @Test
    fun getAllUsersManager() {
        val user = savedUsers.find{it.role == UserRole.MANAGER } ?: fail("Error while setting up demo users.")
        val token = createAuthToken(user, jwtService)

        val result = webTestClient
            .get()
            .uri(endpoint)
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(UserResponse::class.java)
            .returnResult()
            .responseBody

        assertEquals(savedUsers.size, result?.size)
    }

    @Test
    fun getAllUsersUser() {
        val user = savedUsers.find{it.role == UserRole.USER } ?: fail("Error while setting up demo users.")
        val token = createAuthToken(user, jwtService)

        val result = webTestClient
            .get()
            .uri(endpoint)
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(UserResponse::class.java)
            .returnResult()
            .responseBody

        assertEquals(savedUsers.size, result?.size)
    }

    @Test
    fun getAllUsersUnauthenticated() {
        webTestClient
            .get()
            .uri(endpoint)
            .exchange()
            .expectStatus().isUnauthorized
    }

    @Test
    fun getSingleUserManager() {
        val user = savedUsers.find{it.role == UserRole.MANAGER } ?: fail("Error while setting up demo users.")
        val otherUser = savedUsers.find{it.role == UserRole.USER } ?: fail("Error while setting up demo users.")
        val token = createAuthToken(user, jwtService)

        val result = webTestClient
            .get()
            .uri("$endpoint/${otherUser.id}")
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isOk
            .expectBody(UserResponse::class.java)
            .returnResult()
            .responseBody

        assertEquals(otherUser.id, result?.id)
    }

    @Test
    fun getSingleUserUser() {
        val user = savedUsers.find{it.role == UserRole.USER } ?: fail("Error while setting up demo users.")
        val otherUser = savedUsers.find{it.role == UserRole.MANAGER } ?: fail("Error while setting up demo users.")
        val token = createAuthToken(user, jwtService)

        webTestClient
            .get()
            .uri("$endpoint/${otherUser.id}")
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isForbidden
    }

    @Test
    fun getOwnUserManager() {
        val user = savedUsers.find{it.role == UserRole.MANAGER } ?: fail("Error while setting up demo users.")
        val token = createAuthToken(user, jwtService)

        val result = webTestClient
            .get()
            .uri("$endpoint/${user.id}")
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isOk
            .expectBody(UserResponse::class.java)
            .returnResult()
            .responseBody

        assertEquals(user.id, result?.id)
    }

    @Test
    fun getOwnUserUser() {
        val user = savedUsers.find{it.role == UserRole.USER } ?: fail("Error while setting up demo users.")
        val token = createAuthToken(user, jwtService)

        val result = webTestClient
            .get()
            .uri("$endpoint/${user.id}")
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isOk
            .expectBody(UserResponse::class.java)
            .returnResult()
            .responseBody

        assertEquals(user.id, result?.id)
    }


    @Test
    fun getSingleUserUnauthorized() {
        val user = savedUsers.first()

        webTestClient
            .get()
            .uri("$endpoint/${user.id}")
            .exchange()
            .expectStatus().isUnauthorized
    }

    @Test
    fun updateUser() {
        val user = savedUsers.find{it.role == UserRole.USER } ?: fail("Error while setting up demo users.")
        val token = createAuthToken(user, jwtService)
        val userId = user.id ?: fail("Error while setting up demo users.")
        val beforeInDB = userRepository.findById(userId)

        val userUpdate = UserUpdateRequest(
            password = "newPassword"
        )

        webTestClient
            .put()
            .uri("$endpoint/$userId")
            .bodyValue(userUpdate)
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isOk
            .expectBody(UserResponse::class.java)
            .returnResult()
            .responseBody

        val userInDb = userRepository.findById(userId)
        assertTrue(userInDb.isPresent)
        assertNotEquals(userInDb.get().password, beforeInDB.get().password)
    }

    @Test
    fun updateUserByManager() {
        val user = savedUsers.find{it.role == UserRole.MANAGER } ?: fail("Error while setting up demo users.")
        val token = createAuthToken(user, jwtService)
        val otherUser = savedUsers.find{it.role == UserRole.USER } ?: fail("Error while setting up demo users.")
        val beforeInDB = userRepository.findById(otherUser.id!!)

        val userUpdate = UserUpdateRequest(
            password = "newPassword"
        )

        webTestClient
            .put()
            .uri("$endpoint/${otherUser.id}")
            .bodyValue(userUpdate)
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isOk
            .expectBody(UserResponse::class.java)
            .returnResult()
            .responseBody

        val userInDb = userRepository.findById(otherUser.id!!)
        assertTrue(userInDb.isPresent)
        assertNotEquals(beforeInDB.get().password, userInDb.get().password)
    }


    @Test
    fun updateUserUnauthorized() {
        val userId = savedUsers.first().id ?: fail("Issue while storing users")
        val beforeInDB = userRepository.findById(userId)

        val userUpdate = UserUpdateRequest(
            password = "newPassword"
        )
        webTestClient
            .put()
            .uri("$endpoint/$userId")
            .bodyValue(userUpdate)
            .exchange()
            .expectStatus().isUnauthorized

        val userInDb = userRepository.findById(userId)
        assertTrue(userInDb.isPresent)
        assertEquals(userInDb.get().password, beforeInDB.get().password)
    }

    @Test
    fun updateUserForbidden() {
        val user = savedUsers.find{it.role == UserRole.USER } ?: fail("Error while setting up demo users.")
        val token = createAuthToken(user, jwtService)
        val otherUser = savedUsers.find{it.role == UserRole.MANAGER } ?: fail("Error while setting up demo users.")
        val beforeInDB = userRepository.findById(otherUser.id!!)

        val userUpdate = UserUpdateRequest(
            password = "newPassword"
        )

        webTestClient
            .put()
            .uri("$endpoint/${otherUser.id}")
            .bodyValue(userUpdate)
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isForbidden

        val userInDb = userRepository.findById(otherUser.id!!)
        assertTrue(userInDb.isPresent)
        assertEquals(beforeInDB.get().password, userInDb.get().password)
    }
    @Test
    fun deleteUser() {
        val user = savedUsers.find{it.role == UserRole.USER } ?: fail("Error while setting up demo users.")
        val token = createAuthToken(user, jwtService)
        val userId = user.id ?: fail("Error while setting up demo users.")

        webTestClient
            .delete()
            .uri("$endpoint/$userId")
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isNoContent

        val userInDb = userRepository.findById(userId)
        assertTrue(!userInDb.isPresent)
    }

    @Test
    fun deleteUserByManager() {
        val user = savedUsers.find{it.role == UserRole.MANAGER } ?: fail("Error while setting up demo users.")
        val token = createAuthToken(user, jwtService)
        val otherUser = savedUsers.find{it.role == UserRole.USER } ?: fail("Error while setting up demo users.")

        webTestClient
            .delete()
            .uri("$endpoint/${otherUser.id}")
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isNoContent

        val userInDb = userRepository.findById(otherUser.id!!)
        assertTrue(!userInDb.isPresent)
    }


    @Test
    fun deleteUserUnauthorized() {
        val userId = savedUsers.first().id ?: fail("Issue while storing users")

        webTestClient
            .delete()
            .uri("$endpoint/$userId")
            .exchange()
            .expectStatus().isUnauthorized

        val userInDb = userRepository.findById(userId)
        assertTrue(userInDb.isPresent)
    }

    @Test
    fun deleteUserForbidden() {
        val user = savedUsers.find{it.role == UserRole.USER } ?: fail("Error while setting up demo users.")
        val token = createAuthToken(user, jwtService)
        val otherUser = savedUsers.find{it.role == UserRole.MANAGER } ?: fail("Error while setting up demo users.")

        webTestClient
            .delete()
            .uri("$endpoint/${otherUser.id}")
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isForbidden

        val userInDb = userRepository.findById(otherUser.id!!)
        assertTrue(userInDb.isPresent)
    }
}