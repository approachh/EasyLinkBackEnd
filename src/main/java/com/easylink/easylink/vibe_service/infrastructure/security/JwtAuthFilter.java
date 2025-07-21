package com.easylink.easylink.vibe_service.infrastructure.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class JwtAuthFilter implements Filter {

    private PublicKey publicKey;

    public void init() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/keys/public.pem");
        System.out.println("Stream is null? " + (inputStream == null));

        if (inputStream == null) {
            System.err.println("!!! PUBLIC KEY FILE NOT FOUND !!!");
            throw new IllegalStateException("public.pem not found in classpath");
        }
        System.out.println("Public key loaded OK");

        String key = new String(inputStream.readAllBytes())
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);
        publicKey = KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(decoded));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Jwts.parserBuilder()
                        .setSigningKey(publicKey)
                        .build()
                        .parseClaimsJws(token);
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            } catch (JwtException e) {
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing token");
    }
}
