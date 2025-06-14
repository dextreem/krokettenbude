package com.dextreem.croqueteria.integration.controller

import com.dextreem.croqueteria.repository.CommentRepository
import com.dextreem.croqueteria.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

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
    lateinit var croquetteRepository: CommentRepository

    @BeforeEach
    fun setup(){
        commentRepository.deleteAll()
        userRepository.deleteAll()
        croquetteRepository.deleteAll()
    }

    @Test
    fun addComment(){

    }

    @Test
    fun asd(){

    }
}