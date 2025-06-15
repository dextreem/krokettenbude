package com.dextreem.croqueteria.integration.utils

import com.dextreem.croqueteria.entity.Comment
import com.dextreem.croqueteria.entity.Croquette
import com.dextreem.croqueteria.entity.CroquetteForm
import com.dextreem.croqueteria.entity.Rating
import com.dextreem.croqueteria.entity.User
import com.dextreem.croqueteria.entity.UserRole

fun userEntityList() : List<User> = listOf(
    User(
        email = "gerald@riva.com",
        password = "wheresCiri",
        role = UserRole.MANAGER,
    ),
    User(
        email = "cirilla@cintra.com",
        password = "hereIam",
        role = UserRole.USER,
    ),
)

fun croquetteEntityList() : List<Croquette> = listOf(
    Croquette(
        country = "germany",
        name = "Krokette",
        description = "A croquette is a breaded and fried, usually oblong or round dish made from various ingredients, often served as a side dish or tapas. It is typically made from a base such as mashed potatoes, meat, vegetables, or fish, and coated with breadcrumbs.",
        crunchiness = 4,
        spiciness = 1,
        vegan = false,
        form = CroquetteForm.CYLINDRIC,
        imageUrl = ""
    ),
    Croquette(
        country = "germany",
        name = "Krokette",
        description = "Potato-based croquette—mashed potatoes blended with butter, egg yolk, a touch of nutmeg, rolled into cylinders, breaded, and deep‑fried until golden.",
        crunchiness = 4,
        spiciness = 1,
        vegan = false,
        form = CroquetteForm.CYLINDRIC,
        imageUrl = ""
    ),
    Croquette(
        country = "spain",
        name = "Croqueta",
        description = "Classic Spanish ham & béchamel croqueta—thick creamy bechamel mixed with jamón, shaped into small cylinders or disks, coated in breadcrumbs, then fried.",
        crunchiness = 3,
        spiciness = 1,
        vegan = false,
        form = CroquetteForm.DISK,
        imageUrl = ""
    ),
    Croquette(
        country = "japan",
        name = "Korokke",
        description = "Japanese korokke—mashed potatoes mixed with ground beef/pork and onions, patty‑shaped, coated in panko breadcrumbs, and fried to a crispy, fluffy finish.",
        crunchiness = 4,
        spiciness = 2,
        vegan = false,
        form = CroquetteForm.DISK,
        imageUrl = ""
    ),
    Croquette(
        country = "netherlands",
        name = "Kroket",
        description = "Dutch kroket—a meat ragout filling (typically beef) encased in breadcrumbs and deep‑fried, often served in a bun with mustard or as street food.",
        crunchiness = 5,
        spiciness = 1,
        vegan = false,
        form = CroquetteForm.CYLINDRIC,
        imageUrl = ""
    ),
    Croquette(
        country = "belgium",
        name = "Belgian Cheese Croquette",
        description = "Belgian cheese croquette—smooth cheese blended with mash or light béchamel, formed into balls or cylinders, breaded and fried. Nutmeg is a classic seasoning.",
        crunchiness = 4,
        spiciness = 1,
        vegan = false,
        form = CroquetteForm.BALL,
        imageUrl = ""
    )
)
fun ratingEntityList(
    users: List<User>,
    croquettes: List<Croquette>
): List<Rating> {
    return listOf(
        Rating(
            rating = 5,
            croquette = croquettes[1], // id = 2, Germany (detailed Krokette)
            user = users[0],           // Gerald
        ),
        Rating(
            rating = 4,
            croquette = croquettes[2], // id = 3, Spain
            user = users[1],           // Ciri
        ),
        Rating(
            rating = 3,
            croquette = croquettes[2], // id = 3, Spain
            user = users[0],           // Gerald
        ),
        Rating(
            rating = 5,
            croquette = croquettes[3], // id = 4, Japan
            user = users[1],           // Ciri
        ),
        Rating(
            rating = 4,
            croquette = croquettes[3], // id = 4, Japan
            user = users[0],           // Gerald
        ),
        Rating(
            rating = 5,
            croquette = croquettes[4], // id = 5, Netherlands
            user = users[0],           // Gerald
        ),
        Rating(
            rating = 4,
            croquette = croquettes[5], // id = 6, Belgium
            user = users[1],           // Ciri
        ),
        Rating(
            rating = 3,
            croquette = croquettes[5], // id = 6, Belgium
            user = users[0],           // Gerald
        )
    )
}

fun commentEntityList(
    users: List<User>,
    croquettes: List<Croquette>
): List<Comment> {
    val now = java.util.Date()
    return listOf(
        Comment(
            comment = "Crispy and perfectly seasoned. Classic German comfort food!",
            croquette = croquettes[1], // id = 2, detailed German Krokette
            user = users[0],           // Gerald
            createdAt = now,
            updatedAt = now
        ),
        Comment(
            comment = "Not bad, but could use a touch more salt.",
            croquette = croquettes[1], // id = 2
            user = users[1],           // Ciri
            createdAt = now,
            updatedAt = now
        ),
        Comment(
            comment = "Love the jamón flavor! Smooth and rich.",
            croquette = croquettes[2], // id = 3, Spanish Croqueta
            user = users[1],
            createdAt = now,
            updatedAt = now
        ),
        Comment(
            comment = "A bit too soft for my taste, but good flavor.",
            croquette = croquettes[2],
            user = users[0],
            createdAt = now,
            updatedAt = now
        ),
        Comment(
            comment = "Crispy outside, savory inside. Japanese korokke is underrated.",
            croquette = croquettes[3], // id = 4, Japan
            user = users[0],
            createdAt = now,
            updatedAt = now
        ),
        Comment(
            comment = "The panko crust is everything! Would eat again.",
            croquette = croquettes[3], // Japan
            user = users[1],
            createdAt = now,
            updatedAt = now
        ),
        Comment(
            comment = "Cheesy, creamy, and just right. Belgian croquettes rock.",
            croquette = croquettes[5], // id = 6, Belgium
            user = users[1],
            createdAt = now,
            updatedAt = now
        )
    )
}
