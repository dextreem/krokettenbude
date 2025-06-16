package com.dextreem.croqueteria.integration.controller

import com.dextreem.croqueteria.entity.Comment
import com.dextreem.croqueteria.entity.Croquette
import com.dextreem.croqueteria.entity.User
import com.dextreem.croqueteria.integration.utils.commentEntityList
import com.dextreem.croqueteria.integration.utils.createAuthToken
import com.dextreem.croqueteria.integration.utils.croquetteEntityList
import com.dextreem.croqueteria.integration.utils.userEntityList
import com.dextreem.croqueteria.repository.CommentRepository
import com.dextreem.croqueteria.repository.CroquetteRepository
import com.dextreem.croqueteria.repository.UserRepository
import com.dextreem.croqueteria.request.CommentCreateRequest
import com.dextreem.croqueteria.request.CommentUpdateRequest
import com.dextreem.croqueteria.response.CommentResponse
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
import org.springframework.web.util.UriComponentsBuilder
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@Tag("integration")
class CommentControllerIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var commentRepository: CommentRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var croquetteRepository: CroquetteRepository

    @Autowired
    lateinit var jwtService: JwtService

    val endpoint = "/api/v1/comments"

    lateinit var savedUsers: List<User>
    lateinit var savedCroquettes: List<Croquette>
    lateinit var savedComments: List<Comment>

    @BeforeEach
    fun setup() {
        commentRepository.deleteAll()
        userRepository.deleteAll()
        croquetteRepository.deleteAll()

        savedUsers = userRepository.saveAll(userEntityList()).toList()
        savedCroquettes = croquetteRepository.saveAll(croquetteEntityList()).toList()
        savedComments = commentRepository.saveAll(commentEntityList(savedUsers, savedCroquettes)).toList()
    }

    @AfterEach
    fun clearDBs(){
        commentRepository.deleteAll()
        userRepository.deleteAll()
        croquetteRepository.deleteAll()
    }

    @Test
    fun addComment() {
        val token = createAuthToken(savedUsers.first(), jwtService)

        val commentCreateRequest = CommentCreateRequest(
            croquetteId = savedCroquettes.first().id!!,
            comment = "Some Comment"
        )

        val savedComment = webTestClient
            .post()
            .uri(endpoint)
            .header("Authorization", "Bearer $token")
            .bodyValue(commentCreateRequest)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CommentResponse::class.java)
            .returnResult()
            .responseBody

        assertEquals(savedComments.size + 1, commentRepository.findAll().map { it }.size)
        assertTrue(savedComment?.id != null)
    }

    @Test
    fun addCommentNotFound() {
        val token = createAuthToken(savedUsers.first(), jwtService)

        val commentCreateRequest = CommentCreateRequest(
            croquetteId = savedCroquettes.last().id!! + 10,
            comment = "Some Comment"
        )

        webTestClient
            .post()
            .uri(endpoint)
            .header("Authorization", "Bearer $token")
            .bodyValue(commentCreateRequest)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun addCommentUnauthorized() {
        val commentCreateRequest = CommentCreateRequest(
            croquetteId = 42,
            comment = "Some Comment"
        )

        webTestClient
            .post()
            .uri(endpoint)
            .bodyValue(commentCreateRequest)
            .exchange()
            .expectStatus().isUnauthorized

        assertEquals(savedComments.size, commentRepository.findAll().toList().size)
    }

    @Test
    fun getAllComments() {
        val result = webTestClient
            .get()
            .uri(endpoint)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CommentResponse::class.java)
            .returnResult()
            .responseBody

        assertEquals(savedComments.size, result?.size)
    }

    @Test
    fun getAllCommentsForCroquette() {
        val croquetteId = savedComments.first().croquette?.id ?: fail("Issue while setting up demo comments")

        val uri = UriComponentsBuilder.fromUriString(endpoint)
            .queryParam("croquette_id", croquetteId)
            .toUriString()

        val result = webTestClient
            .get()
            .uri(uri)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CommentResponse::class.java)
            .returnResult()
            .responseBody ?: listOf<CommentResponse>()

        assertTrue(result.isNotEmpty())
        assertEquals(savedComments.filter { it.croquette?.id == croquetteId }.size, result.size)
    }

    @Test
    fun getAllCommentsForCroquetteNotFound() {
        val croquetteId = savedComments.last().croquette?.id ?: fail("Issue while setting up demo comments")

        val uri = UriComponentsBuilder.fromUriString(endpoint)
            .queryParam("croquette_id", croquetteId + 10)
            .toUriString()

        webTestClient
            .get()
            .uri(uri)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun updateComment() {
        val user = savedUsers.first()
        val token = createAuthToken(user, jwtService)
        val commentId =
            savedComments.find { it.user?.id == user.id }?.id ?: fail("Issue while setting up demo comments")

        val commentUpdate = CommentUpdateRequest(
            comment = "Some modified comment"
        )

        val result = webTestClient
            .put()
            .uri("$endpoint/$commentId")
            .bodyValue(commentUpdate)
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isOk
            .expectBody(CommentResponse::class.java)
            .returnResult()
            .responseBody

        val commentInDb = commentRepository.findById(commentId)
        assertTrue(commentInDb.isPresent)
        assertEquals(commentUpdate.comment, commentInDb.get().comment)
        assertEquals(commentUpdate.comment, result?.comment)
    }

    @Test
    fun updateCommentNotFound() {
        val user = savedUsers.first()
        val token = createAuthToken(user, jwtService)
        val commentId =
            savedComments.find { it.user?.id == user.id }?.id ?: fail("Issue while setting up demo comments")

        val commentUpdate = CommentUpdateRequest(
            comment = "Some modified comment"
        )

        webTestClient
            .put()
            .uri("$endpoint/${commentId + 100}")
            .bodyValue(commentUpdate)
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isNotFound
    }


    @Test
    fun updateCommentForbidden() {
        val user = savedUsers.first()
        val token = createAuthToken(user, jwtService)
        val commentId =
            savedComments.find { it.user?.id != user.id }?.id ?: fail("Issue while setting up demo comments")

        val commentUpdate = CommentUpdateRequest(
            comment = "Some modified comment"
        )

        webTestClient
            .put()
            .uri("$endpoint/$commentId")
            .bodyValue(commentUpdate)
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isForbidden

        val commentInDb = commentRepository.findById(commentId)
        assertTrue(commentInDb.isPresent)
        assertNotEquals(commentUpdate.comment, commentInDb.get().comment)
    }

    @Test
    fun updateCommentUnauthorized() {
        val commentId = savedComments.first().id ?: fail("Issue while setting up demo comments")

        val commentUpdate = CommentUpdateRequest(
            comment = "Some modified comment"
        )
        webTestClient
            .put()
            .uri("$endpoint/$commentId")
            .bodyValue(commentUpdate)
            .exchange()
            .expectStatus().isUnauthorized

        val commentInDb = commentRepository.findById(commentId)
        assertTrue(commentInDb.isPresent)
        assertNotEquals(commentUpdate.comment, commentInDb.get().comment)
    }

    @Test
    fun deleteComment() {
        val user = savedUsers.first()
        val token = createAuthToken(user, jwtService)
        val commentId =
            savedComments.find { it.user?.id == user.id }?.id ?: fail("Issue while setting up demo comments")


        webTestClient
            .delete()
            .uri("$endpoint/$commentId")
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isNoContent
            .expectBody(CommentResponse::class.java)
            .returnResult()
            .responseBody

        val commentInDb = commentRepository.findById(commentId)
        assertTrue(!commentInDb.isPresent)
        assertEquals(savedComments.size - 1, commentRepository.findAll().toList().size)
    }

    @Test
    fun deleteCommentNotFound() {
        val user = savedUsers.first()
        val token = createAuthToken(user, jwtService)
        val commentId =
            savedComments.find { it.user?.id == user.id }?.id ?: fail("Issue while setting up demo comments")


        webTestClient
            .delete()
            .uri("$endpoint/${commentId + 100}")
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun deleteCommentForbidden() {
        val user = savedUsers.first()
        val token = createAuthToken(user, jwtService)
        val commentId =
            savedComments.find { it.user?.id != user.id }?.id ?: fail("Issue while setting up demo comments")


        webTestClient
            .delete()
            .uri("$endpoint/$commentId")
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isForbidden

        val commentInDb = commentRepository.findById(commentId)
        assertTrue(commentInDb.isPresent)
    }

    @Test
    fun deleteCommentUnauthorized() {
        val commentId = savedComments.first().id ?: fail("Issue while setting up demo comments")

        webTestClient
            .delete()
            .uri("$endpoint/$commentId")
            .exchange()
            .expectStatus().isUnauthorized

        val commentInDb = commentRepository.findById(commentId)
        assertTrue(commentInDb.isPresent)
    }
}