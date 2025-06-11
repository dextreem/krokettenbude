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
@Table(name="USERS")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int?,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    var role: String,

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    var createdAt: Date,

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: Date
)