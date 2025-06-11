package com.dextreem.croqueteria.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.Date

@Entity
@Table(
    name="RATINGS",
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "croquette_id"])]
)
data class Rating (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int?,

    @Column(nullable = false)
    var rating: Int,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "croquette_id", nullable = false)
    val croquette: Croquette? = null,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User? = null,

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    var createdAt: Date,

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: Date
)