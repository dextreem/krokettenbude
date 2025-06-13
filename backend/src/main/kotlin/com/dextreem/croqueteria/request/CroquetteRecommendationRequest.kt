package com.dextreem.croqueteria.request

import com.dextreem.croqueteria.entity.CroquetteForm

data class CroquetteRecommendationRequest(
    val preferredSpiciness: Int,
    val preferredCrunchiness: Int,
    val form: CroquetteForm,
    val vegan: Boolean
)
