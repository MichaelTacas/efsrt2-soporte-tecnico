package com.idat.soportetecnico.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationConverter jwtAuthenticationConverter
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                                // =========================================
                                // LOGIN Y REGISTRO PUBLICO
                                // =========================================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/auth/login",
                                "/api/auth/registro"
                        ).permitAll()


                        // =========================================
                        // SWAGGER PUBLICO
                        // =========================================

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()


                        // =========================================
                        // CLIENTE AUTENTICADO
                        // Solo puede trabajar con sus solicitudes
                        // =========================================

                        .requestMatchers(
                                "/api/cliente/**"
                        ).hasRole("CLIENTE")


                        // =========================================
                        // TECNICO AUTENTICADO
                        // Solo puede trabajar con sus tickets
                        // asignados
                        // =========================================

                        .requestMatchers(
                                "/api/tecnico/**"
                        ).hasRole("TECNICO")


                        // =========================================
                        // MODULOS EXCLUSIVOS DEL ADMINISTRADOR
                        // =========================================

                        .requestMatchers(
                                "/api/admin/**"
                        ).hasRole("ADMINISTRADOR")


                        // =========================================
                        // USUARIOS
                        // Solo administrador
                        // =========================================

                        .requestMatchers(
                                "/api/usuarios/**"
                        ).hasRole("ADMINISTRADOR")


                        // =========================================
                        // CLIENTES GENERALES
                        // Solo administrador
                        // =========================================

                        .requestMatchers(
                                "/api/clientes/**"
                        ).hasRole("ADMINISTRADOR")


                        // =========================================
                        // TECNICOS GENERALES
                        // Solo administrador
                        // =========================================

                        .requestMatchers(
                                "/api/tecnicos/**"
                        ).hasRole("ADMINISTRADOR")


                        // =========================================
                        // SOLICITUDES GENERALES
                        // Solo administrador
                        //
                        // El tecnico usara:
                        // /api/tecnico/solicitudes/**
                        //
                        // El cliente usara:
                        // /api/cliente/solicitudes/**
                        // =========================================

                        .requestMatchers(
                                "/api/solicitudes/**"
                        ).hasRole("ADMINISTRADOR")


                        // =========================================
                        // RESTO DE LA API
                        // Por seguridad queda para ADMIN
                        // =========================================

                        .requestMatchers(
                                "/api/**"
                        ).hasRole("ADMINISTRADOR")


                        // Otros recursos
                        .anyRequest().permitAll()
                )

                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(
                                        jwtAuthenticationConverter
                                )
                        )
                );

        return http.build();
    }


    // =========================================
    // PASSWORD ENCODER
    // =========================================

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    // =========================================
    // AUTHENTICATION MANAGER
    // =========================================

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(
                        userDetailsService
                );

        provider.setPasswordEncoder(
                passwordEncoder
        );

        return new ProviderManager(
                provider
        );
    }


    // =========================================
    // CLAVE JWT
    // =========================================

    @Bean
    public SecretKey jwtSecretKey(
            @Value("${app.jwt.secret}")
            String secretBase64
    ) {

        byte[] keyBytes;

        try {

            keyBytes = Base64
                    .getDecoder()
                    .decode(secretBase64);

        } catch (IllegalArgumentException exception) {

            throw new IllegalStateException(
                    "JWT_SECRET debe estar codificado en Base64",
                    exception
            );
        }

        if (keyBytes.length < 32) {

            throw new IllegalStateException(
                    "JWT_SECRET debe tener como mínimo 32 bytes"
            );
        }

        return new SecretKeySpec(
                keyBytes,
                "HmacSHA256"
        );
    }


    // =========================================
    // JWT ENCODER
    // =========================================

    @Bean
    public JwtEncoder jwtEncoder(
            SecretKey secretKey
    ) {

        return NimbusJwtEncoder
                .withSecretKey(secretKey)
                .build();
    }


    // =========================================
    // JWT DECODER
    // =========================================

    @Bean
    public JwtDecoder jwtDecoder(
            SecretKey secretKey,
            @Value("${app.jwt.issuer:efsrt2-soporte-tecnico}")
            String issuer
    ) {

        NimbusJwtDecoder decoder =
                NimbusJwtDecoder
                        .withSecretKey(secretKey)
                        .macAlgorithm(
                                MacAlgorithm.HS256
                        )
                        .build();

        decoder.setJwtValidator(
                JwtValidators
                        .createDefaultWithIssuer(
                                issuer
                        )
        );

        return decoder;
    }


    // =========================================
    // CONVERSION DE ROLES DEL JWT
    // =========================================

    @Bean
    public JwtAuthenticationConverter
    jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter
                authoritiesConverter =
                new JwtGrantedAuthoritiesConverter();

        authoritiesConverter
                .setAuthoritiesClaimName(
                        "roles"
                );

        authoritiesConverter
                .setAuthorityPrefix(
                        "ROLE_"
                );

        JwtAuthenticationConverter
                authenticationConverter =
                new JwtAuthenticationConverter();

        authenticationConverter
                .setJwtGrantedAuthoritiesConverter(
                        authoritiesConverter
                );

        return authenticationConverter;
    }
}