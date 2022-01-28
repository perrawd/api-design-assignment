package com.lnu.RESTfulCafe.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.experimental.UtilityClass;

import java.io.IOException;

@UtilityClass
public class TokenDecoderUtil {
    public static DecodedJWT decode (String authorizationHeader) throws IOException {
        String token = authorizationHeader.substring("Bearer ".length());
        String property = String.valueOf(PropertyUtil.getProperties().getProperty("app.token.var"));
        Algorithm algorithm = Algorithm.HMAC256(property.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(token);
    }
}
