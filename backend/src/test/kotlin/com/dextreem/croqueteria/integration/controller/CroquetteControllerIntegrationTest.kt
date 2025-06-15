package com.dextreem.croqueteria.integration.controller

import com.dextreem.croqueteria.entity.Croquette
import com.dextreem.croqueteria.entity.CroquetteForm
import com.dextreem.croqueteria.entity.Rating
import com.dextreem.croqueteria.entity.User
import com.dextreem.croqueteria.entity.UserRole
import com.dextreem.croqueteria.integration.utils.createAuthToken
import com.dextreem.croqueteria.integration.utils.croquetteEntityList
import com.dextreem.croqueteria.integration.utils.ratingEntityList
import com.dextreem.croqueteria.integration.utils.userEntityList
import com.dextreem.croqueteria.repository.CroquetteRepository
import com.dextreem.croqueteria.repository.RatingRepository
import com.dextreem.croqueteria.repository.UserRepository
import com.dextreem.croqueteria.request.CroquetteUpdateRequest
import com.dextreem.croqueteria.request.CroquetteCreateRequest
import com.dextreem.croqueteria.response.CroquetteResponse
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
class CroquetteControllerIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var croquetteRepository: CroquetteRepository

    @Autowired
    lateinit var ratingRepository: RatingRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var jwtService: JwtService

    val endpoint = "/api/v1/croquettes"

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
    fun clearDBs() {
        ratingRepository.deleteAll()
        userRepository.deleteAll()
        croquetteRepository.deleteAll()
    }

    @Test
    fun addCroquette() {
        val user = savedUsers.find { it.role == UserRole.MANAGER } ?: fail("Error while setting up demo croquettes.")
        val token = createAuthToken(user, jwtService)

        val croquetteCreateRequest = CroquetteCreateRequest(
            name = "My awesome croquette",
            country = "Riva",
            description = "This is the best and crunchiest croquette in the world!",
            crunchiness = 5,
            spiciness = 5,
            vegan = true,
            form = CroquetteForm.BALL,
            imageUrl = "https://pathToImage"
        )

        val savedCroquette = webTestClient
            .post()
            .uri(endpoint)
            .header("Authorization", "Bearer $token")
            .bodyValue(croquetteCreateRequest)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CroquetteResponse::class.java)
            .returnResult()
            .responseBody

        assertEquals(savedCroquettes.size + 1, croquetteRepository.findAll().map { it }.size)
        assertTrue(savedCroquette?.id != null)
    }

    @Test
    fun addCroquetteForbidden() {
        val user = savedUsers.find { it.role == UserRole.USER } ?: fail("Error while setting up demo croquettes.")
        val token = createAuthToken(user, jwtService)

        val croquetteCreateRequest = CroquetteCreateRequest(
            name = "My awesome croquette",
            country = "Riva",
            description = "This is the best and crunchiest croquette in the world!",
            crunchiness = 5,
            spiciness = 5,
            vegan = true,
            form = CroquetteForm.BALL,
            imageUrl = ""
        )

        webTestClient
            .post()
            .uri(endpoint)
            .header("Authorization", "Bearer $token")
            .bodyValue(croquetteCreateRequest)
            .exchange()
            .expectStatus().isForbidden

        assertEquals(savedCroquettes.size, croquetteRepository.findAll().map { it }.size)
    }

    @Test
    fun addCroquetteUnauthorized() {
        val croquetteCreateRequest = CroquetteCreateRequest(
            name = "My awesome croquette",
            country = "Riva",
            description = "This is the best and crunchiest croquette in the world!",
            crunchiness = 5,
            spiciness = 5,
            vegan = true,
            form = CroquetteForm.BALL,
            imageUrl = ""
        )

        webTestClient
            .post()
            .uri(endpoint)
            .bodyValue(croquetteCreateRequest)
            .exchange()
            .expectStatus().isUnauthorized

        assertEquals(savedCroquettes.size, croquetteRepository.findAll().map { it }.size)
    }

    @Test
    fun getAllCroquettes() {
        val result = webTestClient
            .get()
            .uri(endpoint)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CroquetteResponse::class.java)
            .returnResult()
            .responseBody

        assertEquals(savedCroquettes.size, result?.size)
    }

    @Test
    fun getAllCroquettesFilterBySingleField() {
        val uri = UriComponentsBuilder.fromUriString(endpoint)
            .queryParam("country", "germany")
            .toUriString()

        val result = webTestClient
            .get()
            .uri(uri)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CroquetteResponse::class.java)
            .returnResult()
            .responseBody

        val expNumberOfCroquettes = savedCroquettes.filter { it.country == "germany" }.toList().size

        assertEquals(expNumberOfCroquettes, result?.size)
    }

    @Test
    fun getAllCroquettesFilterByMultipleFields() {
        val uri = UriComponentsBuilder.fromUriString(endpoint)
            .queryParam("crunchiness", listOf(1, 2, 3, 4, 5))
            .queryParam("spiciness", listOf(1, 2, 3))
            .queryParam("vegan", true)
            .toUriString()

        val result = webTestClient
            .get()
            .uri(uri)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CroquetteResponse::class.java)
            .returnResult()
            .responseBody

        val expNumberOfCroquettes =
            savedCroquettes.filter { it.vegan && it.spiciness in 1..3 && it.crunchiness in 1..5 }.toList().size

        assertNotEquals(0, expNumberOfCroquettes)
        assertEquals(expNumberOfCroquettes, result?.size)
    }

    @Test
    fun getCroquetteAverageRating() {
        val croquettes = croquetteRepository.findAll()
        val ratings = ratingRepository.findAll()

        croquettes.forEach { croquette ->
            val averageRating = croquette.averageRating
            val ratingsForThisCroquette = ratings.filter { it.croquette?.id == croquette.id }
            if(!ratingsForThisCroquette.isEmpty()) {
                val meanRating = ratingsForThisCroquette.map { it.rating }.average()
                assertEquals(meanRating, averageRating)
            }
        }
    }

    @Test
    fun getSingleCroquette() {
        val croquette = savedCroquettes.first()

        val result = webTestClient
            .get()
            .uri("$endpoint/${croquette.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(CroquetteResponse::class.java)
            .returnResult()
            .responseBody

        assertEquals(croquette.id, result?.id)
        assertEquals(croquette.name, result?.name)
        assertEquals(croquette.description, result?.description)
    }

    @Test
    fun updateCroquette() {
        val user = savedUsers.find { it.role == UserRole.MANAGER } ?: fail("Error while setting up demo croquettes.")
        val token = createAuthToken(user, jwtService)
        val croquette = savedCroquettes.first()
        val croquetteId = croquette.id ?: fail("Error while setting up demo croquettes.")

        val croquetteUpdate = CroquetteUpdateRequest(
            name = "A new name",
            description = "A new description"
        )

        webTestClient
            .put()
            .uri("$endpoint/$croquetteId")
            .bodyValue(croquetteUpdate)
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isOk
            .expectBody(CroquetteResponse::class.java)
            .returnResult()
            .responseBody

        val croquetteInDb = croquetteRepository.findById(croquetteId)
        assertTrue(croquetteInDb.isPresent)
        assertNotEquals(croquette.name, croquetteInDb.get().name)
        assertNotEquals(croquette.description, croquetteInDb.get().description)
        assertEquals(croquette.spiciness, croquetteInDb.get().spiciness)
        assertEquals(croquette.crunchiness, croquetteInDb.get().crunchiness)
        assertEquals(croquette.vegan, croquetteInDb.get().vegan)
        assertEquals(croquette.form, croquetteInDb.get().form)
        assertEquals(croquette.country, croquetteInDb.get().country)
    }

    @Test
    fun updateCroquetteForbidden() {
        val user = savedUsers.find { it.role == UserRole.USER } ?: fail("Error while setting up demo croquettes.")
        val token = createAuthToken(user, jwtService)
        val croquette = savedCroquettes.first()
        val croquetteId = croquette.id ?: fail("Error while setting up demo croquettes.")

        val croquetteUpdate = CroquetteUpdateRequest(
            name = "A new name",
            description = "A new description"
        )

        webTestClient
            .put()
            .uri("$endpoint/$croquetteId")
            .bodyValue(croquetteUpdate)
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isForbidden

        val croquetteInDb = croquetteRepository.findById(croquetteId)
        assertTrue(croquetteInDb.isPresent)
        assertEquals(croquette.name, croquetteInDb.get().name)
        assertEquals(croquette.description, croquetteInDb.get().description)
        assertEquals(croquette.spiciness, croquetteInDb.get().spiciness)
        assertEquals(croquette.crunchiness, croquetteInDb.get().crunchiness)
        assertEquals(croquette.vegan, croquetteInDb.get().vegan)
        assertEquals(croquette.form, croquetteInDb.get().form)
        assertEquals(croquette.country, croquetteInDb.get().country)
    }


    @Test
    fun updateCroquetteUnauthorized() {
        val croquette = savedCroquettes.first()
        val croquetteId = croquette.id ?: fail("Error while setting up demo croquettes.")

        val croquetteUpdate = CroquetteUpdateRequest(
            name = "A new name",
            description = "A new description"
        )

        webTestClient
            .put()
            .uri("$endpoint/$croquetteId")
            .bodyValue(croquetteUpdate)
            .exchange()
            .expectStatus().isUnauthorized

        val croquetteInDb = croquetteRepository.findById(croquetteId)
        assertTrue(croquetteInDb.isPresent)
        assertEquals(croquette.name, croquetteInDb.get().name)
        assertEquals(croquette.description, croquetteInDb.get().description)
        assertEquals(croquette.spiciness, croquetteInDb.get().spiciness)
        assertEquals(croquette.crunchiness, croquetteInDb.get().crunchiness)
        assertEquals(croquette.vegan, croquetteInDb.get().vegan)
        assertEquals(croquette.form, croquetteInDb.get().form)
        assertEquals(croquette.country, croquetteInDb.get().country)
    }


    @Test
    fun deleteCroquette() {
        val user = savedUsers.find { it.role == UserRole.MANAGER } ?: fail("Error while setting up demo croquettes.")
        val token = createAuthToken(user, jwtService)
        val croquette = savedCroquettes.first()
        val croquetteId = croquette.id ?: fail("Error while setting up demo croquettes.")

        webTestClient
            .delete()
            .uri("$endpoint/$croquetteId")
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isNoContent

        val croquetteInDb = croquetteRepository.findById(croquetteId)
        assertTrue(!croquetteInDb.isPresent)
    }

    @Test
    fun deleteCroquetteForbidden() {
        val user = savedUsers.find { it.role == UserRole.USER } ?: fail("Error while setting up demo croquettes.")
        val token = createAuthToken(user, jwtService)
        val croquette = savedCroquettes.first()
        val croquetteId = croquette.id ?: fail("Error while setting up demo croquettes.")

        webTestClient
            .delete()
            .uri("$endpoint/$croquetteId")
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isForbidden

        val croquetteInDb = croquetteRepository.findById(croquetteId)
        assertTrue(croquetteInDb.isPresent)
    }


    @Test
    fun deleteCroquetteUnauthorized() {
        val croquette = savedCroquettes.first()
        val croquetteId = croquette.id ?: fail("Error while setting up demo croquettes.")

        webTestClient
            .delete()
            .uri("$endpoint/$croquetteId")
            .exchange()
            .expectStatus().isUnauthorized

        val croquetteInDb = croquetteRepository.findById(croquetteId)
        assertTrue(croquetteInDb.isPresent)
    }

}