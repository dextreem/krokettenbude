package com.dextreem.croqueteria.integration.controller

import com.dextreem.croqueteria.entity.Comment
import com.dextreem.croqueteria.entity.Croquette
import com.dextreem.croqueteria.entity.Rating
import com.dextreem.croqueteria.entity.User
import com.dextreem.croqueteria.integration.utils.commentEntityList
import com.dextreem.croqueteria.integration.utils.croquetteEntityList
import com.dextreem.croqueteria.integration.utils.ratingEntityList
import com.dextreem.croqueteria.integration.utils.userEntityList
import com.dextreem.croqueteria.repository.CommentRepository
import com.dextreem.croqueteria.repository.CroquetteRepository
import com.dextreem.croqueteria.repository.RatingRepository
import com.dextreem.croqueteria.repository.UserRepository
import com.dextreem.croqueteria.request.CroquetteRecommendationRequest
import com.dextreem.croqueteria.response.CroquetteRecommendationResponse
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
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
class RecommendationControllerIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var commentRepository: CommentRepository

    @Autowired
    lateinit var ratingRepository: RatingRepository

    @Autowired
    lateinit var croquetteRepository: CroquetteRepository

    @Autowired
    lateinit var userRepository: UserRepository

    val endpoint = "/api/v1/recommendations"

    lateinit var savedCroquettes: List<Croquette>
    lateinit var savedComments: List<Comment>
    lateinit var savedRatings: List<Rating>
    lateinit var savedUsers: List<User>

    @BeforeEach
    fun setup() {
        commentRepository.deleteAll()
        ratingRepository.deleteAll()
        userRepository.deleteAll()
        croquetteRepository.deleteAll()

        savedUsers = userRepository.saveAll(userEntityList()).toList()
        savedCroquettes = croquetteRepository.saveAll(croquetteEntityList()).toList()
        savedComments = commentRepository.saveAll(commentEntityList(savedUsers, savedCroquettes)).toList()
        savedRatings = ratingRepository.saveAll(ratingEntityList(savedUsers, savedCroquettes)).toList()
    }

    @AfterEach
    fun clearDBs(){
        commentRepository.deleteAll()
        ratingRepository.deleteAll()
        userRepository.deleteAll()
        croquetteRepository.deleteAll()
    }

    @Test
    fun requestRecommendationPerfectMatch() {
        val croquetteRecommendationRequest = savedCroquettes.first().let {
            CroquetteRecommendationRequest(
                preferredSpiciness = it.spiciness,
                preferredCrunchiness = it.crunchiness,
                form = it.form,
                vegan = it.vegan
            )
        }

        val result = webTestClient
            .post()
            .uri(endpoint)
            .bodyValue(croquetteRecommendationRequest)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CroquetteRecommendationResponse::class.java)
            .returnResult()
            .responseBody ?: listOf<CroquetteRecommendationResponse>()

        assertTrue(result.isNotEmpty())
        assertEquals(0, result.first().score) // Perfect match is equal to a 0 score
    }

    @Test
    fun requestRecommendationCloseMatch() {
        val croquetteRecommendationRequest = savedCroquettes.first().let {
            CroquetteRecommendationRequest(
                preferredSpiciness = it.spiciness - 1,
                preferredCrunchiness = it.crunchiness,
                form = it.form,
                vegan = it.vegan
            )
        }

        val result = webTestClient
            .post()
            .uri(endpoint)
            .bodyValue(croquetteRecommendationRequest)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CroquetteRecommendationResponse::class.java)
            .returnResult()
            .responseBody ?: listOf<CroquetteRecommendationResponse>()

        assertTrue(result.isNotEmpty())
        assertNotEquals(0, result.first().score) // Perfect match is equal to a 0 score
    }

}