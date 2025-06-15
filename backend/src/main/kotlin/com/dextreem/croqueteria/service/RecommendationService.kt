package com.dextreem.croqueteria.service

import com.dextreem.croqueteria.repository.CroquetteRepository
import com.dextreem.croqueteria.request.CroquetteRecommendationRequest
import com.dextreem.croqueteria.response.CroquetteRecommendationResponse
import org.springframework.stereotype.Service
import kotlin.math.abs

@Service
class RecommendationService(
    private val croquetteRepository: CroquetteRepository
) {

    fun recommendCroquettes(request: CroquetteRecommendationRequest): List<CroquetteRecommendationResponse> {
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
            .toList()
    }
}
