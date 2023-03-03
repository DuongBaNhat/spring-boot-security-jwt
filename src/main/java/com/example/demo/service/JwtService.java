package com.example.demo.service;

import com.example.demo.entities.User;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Autowired
    private Environment env;

    private static final String USERNAME = "username";

    public String login(User user) {
        return "";
    }
    public String genTokenLogin(String username) {
        String token = null;
        try {
            JWSSigner signer = new MACSigner(genShareSecret());

            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            builder.claim(USERNAME, username);
            builder.expirationTime(genExpDate());
            JWTClaimsSet claimsSet = builder.build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            // aplly
            signedJWT.sign(signer);

            token = signedJWT.serialize();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }

    private JWTClaimsSet getClaimsFromToken(String token) {
        JWTClaimsSet claims = null;
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(genShareSecret());
            if(signedJWT.verify(verifier)) {
                claims = signedJWT.getJWTClaimsSet();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  claims;
    }

    private Date genExpDate() {
        int expire_time = Integer.parseInt(env.getProperty("exp_test"));
        return new Date(System.currentTimeMillis() + expire_time);
    }

    private  Date getExpDateFromToken(String token) {
        Date expiration = null;
        JWTClaimsSet claims = getClaimsFromToken(token);
        expiration = claims.getExpirationTime();

        return expiration;
    }

    public  String getUsernameFromToken(String token) {
        String username = null;
        try {
            JWTClaimsSet claims = getClaimsFromToken(token);
            username = claims.getStringClaim(USERNAME);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return username;
    }

    private byte[] genShareSecret() {
        byte[] sharedSecret = new byte[32];
        String secret_key = env.getProperty("secret");
        sharedSecret = secret_key.getBytes();

        return sharedSecret;
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = getExpDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateTokenLogin(String token) {
        if(token == null || token.trim().length() == 0) {
            return  false;
        }
        String username = getUsernameFromToken(token);

        if(username == null || username.isEmpty()) {
            return false;
        }
        if(isTokenExpired(token)) {
            return  false;
        }

        return true;
    }
}
