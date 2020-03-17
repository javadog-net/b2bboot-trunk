package com.jhmis.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jhmis.common.config.Global;

import java.util.Date;
import java.util.Map;

public class JWTUtils {

    // 过期时间 默认是1天  实际是按照 config里面的yml文件设置的
    private static final long EXPIRE_TIME = Global.getConfigAsLong("jwt.expireTime",24 * 60 * 60) ;
    //系统秘钥
    private static final String SECRET = Global.getConfig("jwt.secret","452000");
    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @return 是否正确
     */
    public static boolean verify(String token, String userId, String userType) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userId",userId)
                    .withClaim("userType",userType)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     */
    public static String getUserType(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userType").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     */
    public static Map<String, Claim> getClaims(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaims();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    /**
     * 生成签名,指定时间后过期,一经生成不可修改，令牌在指定时间内一直有效
     *
     * @param userId 用户ID
     * @param userType 用户类型
     * @return 加密的token
     */
    public static String sign(String userId, String userType) {
        try {
            final Date createdDate = new Date();
            final Date expirationDate = new Date(createdDate.getTime() + EXPIRE_TIME * 1000);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            // 附带username信息
            return JWT.create()
                    .withClaim("userId", userId)
                    .withClaim("userType", userType)
                    .withIssuedAt(createdDate)
                    .withExpiresAt(expirationDate)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }

    }
}
