package com.dextreem.croqueteria.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.Date

@Entity
@Table(name="CROQUETTES")
data class Croquette (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int?,

    @Column(nullable = false)
    var country: String,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var crunchiness: Int,

    @Column(nullable = false)
    var spiciness: Int,

    @Column(nullable = false)
    var vegan: Boolean,

    @Column(nullable = false)
    var form: String,

    @Column(name="image_url")
    var imageUrl: String,

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    var createdAt: Date,

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: Date
)