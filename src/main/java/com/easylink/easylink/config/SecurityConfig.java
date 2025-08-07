package com.easylink.easylink.config;
//import org.flywaydb.core.internal.resource.classpath.ClassPathResource;
import org.springframework.beans.factory.annotation.Value;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.ClassPathResource;


@Configuration
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/", "/index.html", "/static/**", "/assets/**", "/favicon.ico").permitAll()
                        .requestMatchers("/", "/index.html", "/static/**", "/assets/**", "/favicon.ico", "/view/**","/clearviewblue.png" ).permitAll()
                        .requestMatchers("/.well-known/jwks.json").permitAll()
                        .requestMatchers("/api/v3/auth/start").permitAll()
                        .requestMatchers("/api/v3/auth/check").permitAll()
                        .requestMatchers("/api/v3/vibes/**").permitAll()
                        .requestMatchers("/api/v3/auth/signup").permitAll()
                        .requestMatchers("/api/v3/reviews/**").permitAll()
                        .requestMatchers("/api/v3/upload/**").permitAll()
                        .requestMatchers("/api/v3/catalog**").permitAll()
                        .requestMatchers("/api/v3/offers/**").permitAll()
                        .requestMatchers("/api/v3/auth/question-templates").permitAll()
                        .requestMatchers("/view/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwkSetUri(jwkSetUri)
                        )
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Разрешаем запросы только с конкретного домена (или используйте "*" для всех)
        config.addAllowedOrigin("*");
        // config.addAllowedOrigin("*");

        config.addAllowedMethod("*");    // Разрешены все HTTP методы
        config.addAllowedHeader("*");    // Разрешены все заголовки
        // Если требуется отправлять credentials, раскомментируйте:
        // config.setAllowCredentials(true);

        // Регистрируем конфигурацию для всех маршрутов:
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean //Added 27.03
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter(CorsConfigurationSource corsConfigurationSource) {
        return new CorsFilter(corsConfigurationSource);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RSAPublicKey publicKey() throws Exception {
        var resource = new ClassPathResource("keys/public.pem");

        try (InputStream is = resource.getInputStream()) {
            String key = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            key = key.replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(key);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            return (RSAPublicKey) kf.generatePublic(spec);
        }
    }
}