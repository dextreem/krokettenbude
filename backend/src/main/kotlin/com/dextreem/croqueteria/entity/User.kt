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
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Date

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "USERS")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    @Column(nullable = false, unique = true)
    private var email: String,

    @Column(nullable = false)
    private var password: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: UserRole,

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    var createdAt: Date? = null,

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: Date? = null
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority?> = setOf(SimpleGrantedAuthority(role.authority))
    override fun getPassword(): String = password
    override fun getUsername(): String = email

    fun setEmail(newEmail: String) {
        this.email = newEmail
    }

    fun setPassword(newPassword: String) {
        this.password = newPassword
    }

}
