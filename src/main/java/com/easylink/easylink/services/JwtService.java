package com.easylink.easylink.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.web.server.ServerSecurityMarker;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private PrivateKey privateKey;

    @PostConstruct
    public void init() throws Exception{
        InputStream inputStream = getClass().getResourceAsStream("/keys/private.pem");
        String key = new String(inputStream.readAllBytes())
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);
        privateKey = KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decoded));
    }

    public String generateToken(String id){
        return Jwts.builder()
                .setSubject(id)
                .claim("role","USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+3600_000))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }
}
