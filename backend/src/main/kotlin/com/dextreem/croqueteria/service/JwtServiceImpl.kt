package com.dextreem.croqueteria.service
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtServiceImpl : JwtService {

    @Value("\${spring.croqueteria.jwt.secret}")
    private lateinit var secretKey: String

    @Value("\${spring.croqueteria.jwt.expiration}")
    private var jwtExpiration: Long = 0

    private fun getSigningKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    private fun getParser(): JwtParser {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
    }

    override fun extractUsername(token: String): String {
        return extractClaim(token) { it.subject }
    }

    override fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    override fun generateToken(claims: Map<String, Any>, userDetails: UserDetails): String {
        val now = Date()
        val expiryDate = Date(now.time + jwtExpiration)

        return Jwts.builder()
            .claims(claims)
            .subject(userDetails.username)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(getSigningKey())
            .compact()
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token) { it.expiration }
    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = getParser().parseSignedClaims(token).payload
        return claimsResolver(claims)
    }
}
