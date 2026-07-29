package com.duzheStudy.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.duzheStudy.modules.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import com.auth0.jwt.exceptions.JWTVerificationException;
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    public static final long EXPIRE_TIME = 60L* 60 * 1000*25;

    public static final String SECRET = "SECRET";

    //签发token
    public static String sign(User user){
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return JWT.create()
                .withClaim("id",user.getId())
                .withClaim("userName", user.getUserName())
                .withClaim("password", user.getPassword())
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC256(SECRET));
    }

//    //校验token
//    public static boolean verify(String token){
//        try {
//            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
//            DecodedJWT decodedJWT  = verifier.verify(token);
//            return true;
//        }catch (Exception e){
//            return false;
//        }
//    }
// 校验 token
public static boolean verify(String token) {
    try {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        verifier.verify(token); // 如果验证失败，会抛出异常
        logger.debug("Token verified successfully: {}", token);
        return true;
    } catch (JWTVerificationException e) {
        logger.warn("Token verification failed: {} - {}", token, e.getMessage());
        return false;
    } catch (Exception e) {
        logger.error("Unexpected error during token verification: {} - {}", token, e.getMessage(), e);
        return false;
    }
}

    //获取token内的携带的用户信息
    public static User getUserByToken(String token){
        DecodedJWT decodedJWT = JWT.decode(token);
        User user = new User();
        user.setUserName(decodedJWT.getClaim("userName").asString());
        user.setPassword(decodedJWT.getClaim("password").asString());
        user.setId(decodedJWT.getClaim("id").asLong());
        return user;
    }

}
