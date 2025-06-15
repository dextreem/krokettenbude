package com.dextreem.croqueteria.integration.controller

import com.dextreem.croqueteria.entity.Croquette
import com.dextreem.croqueteria.entity.Rating
import com.dextreem.croqueteria.entity.User
import com.dextreem.croqueteria.integration.utils.createAuthToken
import com.dextreem.croqueteria.integration.utils.croquetteEntityList
import com.dextreem.croqueteria.integration.utils.ratingEntityList
import com.dextreem.croqueteria.integration.utils.userEntityList
import com.dextreem.croqueteria.repository.CroquetteRepository
import com.dextreem.croqueteria.repository.RatingRepository
import com.dextreem.croqueteria.repository.UserRepository
import com.dextreem.croqueteria.request.RatingUpdateRequest
import com.dextreem.croqueteria.request.RatingCreateRequest
import com.dextreem.croqueteria.response.RatingResponse
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
class RatingControllerIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var ratingRepository: RatingRepository

    @Autowired
    lateinit var userRepository: UserRepository


    @Autowired
    lateinit var croquetteRepository: CroquetteRepository

    @Autowired
    lateinit var jwtService: JwtService

    val endpoint = "/api/v1/ratings"

    lateinit var savedUsers: List<User>
    lateinit var savedCroquettes: List<Croquette>
    lateinit var savedRatings: List<Rating>

    @BeforeEach
    fun setup() {
        ratingRepository.deleteAll()
        userRepository.deleteAll()
        croquetteRepository.deleteAll()

        savedUsers = userRepository.saveAll(userEntityList()).toList()
        savedCroquettes = croquetteRepository.saveAll(croquetteEntityList()).toList()
        savedRatings = ratingRepository.saveAll(ratingEntityList(savedUsers, savedCroquettes)).toList()
    }

    @AfterEach
    fun clearDBs(){
        ratingRepository.deleteAll()
        userRepository.deleteAll()
        croquetteRepository.deleteAll()
    }

    @Test
    fun addRating() {
        val token = createAuthToken(savedUsers.first(), jwtService)

        val ratingCreateRequest = RatingCreateRequest(
            croquetteId = savedCroquettes.first().id!!,
            rating = 5
        )

        val savedRating = webTestClient
            .post()
            .uri(endpoint)
            .header("Authorization", "Bearer $token")
            .bodyValue(ratingCreateRequest)
            .exchange()
            .expectStatus().isCreated
            .expectBody(RatingResponse::class.java)
            .returnResult()
            .responseBody

        assertEquals(savedRatings.size + 1, ratingRepository.findAll().map { it }.size)
        assertTrue(savedRating?.id != null)
    }

    @Test
    fun addRatingUnauthorized() {
        val ratingCreateRequest = RatingCreateRequest(
            croquetteId = 42,
            rating = 4
        )

        webTestClient
            .post()
            .uri(endpoint)
            .bodyValue(ratingCreateRequest)
            .exchange()
            .expectStatus().isUnauthorized

        assertEquals(savedRatings.size, ratingRepository.findAll().toList().size)
    }

    @Test
    fun getAllRatings() {
        val result = webTestClient
            .get()
            .uri(endpoint)
            .exchange()
            .expectStatus().isOk
            .expectBody(List::class.java)
            .returnResult()
            .responseBody

        assertEquals(savedRatings.size, result?.size)
    }

    @Test
    fun getAllRatingsForCroquette() {
        val croquetteId = savedRatings.first().croquette?.id ?: fail("Issue while setting up demo ratings")

        val uri = UriComponentsBuilder.fromUriString(endpoint)
            .queryParam("croquette_id", croquetteId)
            .toUriString()

        val result = webTestClient
            .get()
            .uri(uri)
            .exchange()
            .expectStatus().isOk
            .expectBody(List::class.java)
            .returnResult()
            .responseBody ?: listOf<RatingResponse>()

        assertTrue(result.isNotEmpty())
        assertEquals(savedRatings.filter { it.croquette?.id == croquetteId }.size, result.size)
    }

    @Test
    fun updateRating() {
        val user = savedUsers.first()
        val token = createAuthToken(user, jwtService)
        val ratingId =
            savedRatings.find { it.user?.id == user.id }?.id ?: fail("Issue while setting up demo ratings")

        val ratingUpdate = RatingUpdateRequest(
            rating = 4
        )

        val result = webTestClient
            .put()
            .uri("$endpoint/$ratingId")
            .bodyValue(ratingUpdate)
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isOk
            .expectBody(RatingResponse::class.java)
            .returnResult()
            .responseBody

        val ratingInDb = ratingRepository.findById(ratingId)
        assertTrue(ratingInDb.isPresent)
        assertEquals(ratingUpdate.rating, ratingInDb.get().rating)
        assertEquals(ratingUpdate.rating, result?.rating)
    }


    @Test
    fun updateRatingForbidden() {
        val user = savedUsers.first()
        val token = createAuthToken(user, jwtService)
        val ratingId =
            savedRatings.find { it.user?.id != user.id }?.id ?: fail("Issue while setting up demo ratings")

        val ratingUpdate = RatingUpdateRequest(
            rating = 3
        )

        webTestClient
            .put()
            .uri("$endpoint/$ratingId")
            .bodyValue(ratingUpdate)
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isForbidden

        val ratingInDb = ratingRepository.findById(ratingId)
        assertTrue(ratingInDb.isPresent)
        assertNotEquals(ratingUpdate.rating, ratingInDb.get().rating)
    }

    @Test
    fun updateRatingUnauthorized() {
        val ratingId = savedRatings.first().id ?: fail("Issue while setting up demo ratings")

        val ratingUpdate = RatingUpdateRequest(
            rating = 4
        )
        webTestClient
            .put()
            .uri("$endpoint/$ratingId")
            .bodyValue(ratingUpdate)
            .exchange()
            .expectStatus().isUnauthorized

        val ratingInDb = ratingRepository.findById(ratingId)
        assertTrue(ratingInDb.isPresent)
        assertNotEquals(ratingUpdate.rating, ratingInDb.get().rating)
    }

    @Test
    fun deleteRating() {
        val user = savedUsers.first()
        val token = createAuthToken(user, jwtService)
        val ratingId =
            savedRatings.find { it.user?.id == user.id }?.id ?: fail("Issue while setting up demo ratings")


        webTestClient
            .delete()
            .uri("$endpoint/$ratingId")
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isNoContent
            .expectBody(RatingResponse::class.java)
            .returnResult()
            .responseBody

        val ratingInDb = ratingRepository.findById(ratingId)
        assertTrue(!ratingInDb.isPresent)
        assertEquals(savedRatings.size - 1, ratingRepository.findAll().toList().size)
    }

    @Test
    fun deleteRatingForbidden() {
        val user = savedUsers.first()
        val token = createAuthToken(user, jwtService)
        val ratingId =
            savedRatings.find { it.user?.id != user.id }?.id ?: fail("Issue while setting up demo ratings")


        webTestClient
            .delete()
            .uri("$endpoint/$ratingId")
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isForbidden

        val ratingInDb = ratingRepository.findById(ratingId)
        assertTrue(ratingInDb.isPresent)
    }

    @Test
    fun deleteRatingUnauthorized() {
        val ratingId = savedRatings.first().id ?: fail("Issue while setting up demo ratings")

        webTestClient
            .delete()
            .uri("$endpoint/$ratingId")
            .exchange()
            .expectStatus().isUnauthorized

        val ratingInDb = ratingRepository.findById(ratingId)
        assertTrue(ratingInDb.isPresent)
    }
}