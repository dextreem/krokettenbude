package com.dextreem.croqueteria.config

import com.dextreem.croqueteria.config.entrypoint.CustomAccessDeniedHandler
import com.dextreem.croqueteria.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    val userRepository: UserRepository,
    val jwtAuthFilter: JwtAuthFilter,
    val customAccessDeniedHandler: CustomAccessDeniedHandler,
    val customAuthenticationEntryPoint: AuthenticationEntryPoint
) {

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->
            userRepository.findByEmail(username)
                .orElseThrow { UsernameNotFoundException("User not found") }
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
//                        "/api/v1/ratings/**",
                        "/api/v1/comments/**"
                    ).permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/users/**", "/api/v1/recommendations/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/croquettes/**").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/croquettes/**").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/croquettes/**").hasRole("MANAGER")
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
            .csrf { csrf -> csrf.disable() }
            .exceptionHandling {
                it.authenticationEntryPoint(customAuthenticationEntryPoint)
                it.accessDeniedHandler(customAccessDeniedHandler)
            }
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

}