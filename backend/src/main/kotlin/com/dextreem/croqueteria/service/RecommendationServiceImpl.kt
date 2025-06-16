package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.entity.CroquetteForm
import com.dextreem.croqueteria.repository.CroquetteRepository
import com.dextreem.croqueteria.request.CroquetteLLMRecommendationRequest
import com.dextreem.croqueteria.request.CroquetteRecommendationRequest
import com.dextreem.croqueteria.request.CroquetteRecommendationRequestWrapper
import com.dextreem.croqueteria.response.CroquetteRecommendationResponse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.math.abs

@Service
class RecommendationServiceImpl(
    private val croquetteRepository: CroquetteRepository
) : RecommendationService {
    companion object : KLogging()

    private val client = HttpClient.newHttpClient()
    private val mapper = jacksonObjectMapper()

    @Transactional(readOnly = true)
    override fun recommendCroquettes(request: CroquetteRecommendationRequest): List<CroquetteRecommendationResponse> {
        logger.info("Recommending croquettes requested by user.")
        val allCroquettes = croquetteRepository.findAll()

        return allCroquettes
            .map { croquette ->
                val spicinessDiff = abs(croquette.spiciness - request.preferredSpiciness)
                val crunchinessDiff = abs(croquette.crunchiness - request.preferredCrunchiness)
                val veganPenalty = if (croquette.vegan != request.vegan) 10 else 0
                val formPenalty = if (croquette.form != request.form) 5 else 0
                val score = spicinessDiff + crunchinessDiff + veganPenalty + formPenalty
                CroquetteRecommendationResponse(
                    id = croquette.id,
                    name = croquette.name,
                    country = croquette.country,
                    description = croquette.description,
                    crunchiness = croquette.crunchiness,
                    spiciness = croquette.spiciness,
                    vegan = croquette.vegan,
                    form = croquette.form.form,
                    imageUrl = croquette.imageUrl,
                    createdAt = croquette.createdAt,
                    updatedAt = croquette.updatedAt,
                    averageRating = croquette.averageRating,
                    score = score
                )
            }
            .sortedBy { it.score }
            .take(10)
    }

    @Transactional(readOnly = true)
    override fun recommendCroquetteLlm(request: CroquetteLLMRecommendationRequest): List<CroquetteRecommendationResponse> {
        logger.info("LLM recommending process requested by user")

        val prompt = """
            Extract a JSON object like {"preferredSpiciness": 4, "preferredCrunchiness": 5, "vegan": true, "form": "CYLINDRIC"} where preferredSpiciness is an integer number in range 1-5 and form is an String enum "CYLINDRIC", "BALL", "OVAL", "DISK", and "OTHER", vegan is a boolean true or false.
            Extract from the following sentence: "${request.description}"
            
            Put the resulting JSON in markdown code.
        """.trimIndent()

        logger.info("Prompt: $prompt")

        val requestJson = mapper.writeValueAsString(
            mapOf("model" to "deepseek-r1:1.5b", "prompt" to prompt, "stream" to false)
        )

        val httpRequest = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:11434/api/generate"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestJson))
            .build()

        val response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString())
        val responseJson = mapper.readTree(response.body())
        val rawResponse = responseJson["response"].asText()

        logger.info("LLM response: $rawResponse")

        val regex = Regex("```json\\s*(.*?)\\s*```", RegexOption.DOT_MATCHES_ALL)
        val match = regex.find(rawResponse)
            val cleaned = match?.groups?.get(1)?.value
                ?: throw IllegalArgumentException("Could not find a proper recommendation based on your input.")


        try {
            val extractedWrapper: CroquetteRecommendationRequestWrapper = mapper.readValue(cleaned)
            return recommendCroquettes(extractedWrapper.toRecommendationRequest())
        }catch (ex: Exception){
            logger.error(ex.message)
            ex.printStackTrace()
            throw IllegalArgumentException("Could not find a proper recommendation based on your input.")
        }
    }

    private fun CroquetteRecommendationRequestWrapper.toRecommendationRequest(): CroquetteRecommendationRequest {
        return CroquetteRecommendationRequest(
            preferredSpiciness = this.preferredSpiciness ?: 3,
            preferredCrunchiness = this.preferredCrunchiness ?: 3,
            vegan = this.vegan ?: false,
            form = CroquetteForm.entries.find { it.form.equals(form, ignoreCase = true) }
                ?: CroquetteForm.CYLINDRIC
        )
    }
}
