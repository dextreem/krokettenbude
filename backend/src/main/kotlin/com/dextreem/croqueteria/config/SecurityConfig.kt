package com.dextreem.croqueteria.config

import com.dextreem.croqueteria.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter




@Configuration
@EnableWebSecurity
class SecurityConfig(val userRepository: UserRepository, val jwtAuthFilter: JwtAuthFilter) {

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->
            val user = userRepository.findByEmail(username)
                .orElseThrow { UsernameNotFoundException("User not found") }

            org.springframework.security.core.userdetails.User(
                user.email,
                user.password,
                listOf() // or user.roles.map { SimpleGrantedAuthority(it.name) }
            )
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager? {
        return config.getAuthenticationManager()
    }

    @Bean
    fun authenticationEntryPoint(): AuthenticationEntryPoint {
        return AuthenticationEntryPoint { request: HttpServletRequest?, response: HttpServletResponse?, ex: AuthenticationException? ->
            response!!.status = HttpStatus.UNAUTHORIZED.value()
            response.contentType = "application/json"
            response.setHeader("WWW-Authenticate", "")
            response.writer.write("{\"error\": \"Unauthorized access\"}")
        }
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .headers { headers ->
                headers.frameOptions { frameOptions ->
                    frameOptions.sameOrigin()
                }
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        HttpMethod.GET,
                        "/api/v1/croquettes/**",
                        "/api/v1/ratings/**",
                        "/api/v1/comments/**"
                    ).permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/croquettes/**").hasRole("manager")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/croquettes/**").hasRole("manager")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/croquettes/**").hasRole("manager")
                    .requestMatchers(
                        "/docs",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/h2-console/**"
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .httpBasic { httpBasicCustomizer -> httpBasicCustomizer.disable() }
            .csrf { csrf ->
                csrf.ignoringRequestMatchers("/h2-console/**") // disable CSRF for h2 console
            }
            .exceptionHandling({ exceptionHandling ->
                exceptionHandling
                    .authenticationEntryPoint(authenticationEntryPoint())
            })
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

}