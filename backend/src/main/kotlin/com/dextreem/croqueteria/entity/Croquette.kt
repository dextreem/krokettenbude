package com.dextreem.croqueteria.entity

import jakarta.persistence.Cacheable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Formula
import org.hibernate.annotations.UpdateTimestamp
import java.util.Date

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "CROQUETTES")
data class Croquette(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    @Column(nullable = false)
    var country: String,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, length = 1000)
    var description: String,

    @Column(nullable = false)
    var crunchiness: Int,

    @Column(nullable = false)
    var spiciness: Int,

    @Column(nullable = false)
    var vegan: Boolean,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var form: CroquetteForm,

    @Column(name = "image_url")
    var imageUrl: String,

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    var createdAt: Date? = null,

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: Date? = null,

    @Formula("(SELECT AVG(r.rating) FROM RATINGS r WHERE r.croquette_id = id)")
    var averageRating: Double? = null
)