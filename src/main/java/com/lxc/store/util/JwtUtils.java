package com.lxc.store.util;




import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

/**
 * @author xc
 * @date 2022/7/16 15:44
 */
public class JwtUtils {
    /**
     * 创建Token
     * @param ttlMillis 过期时间
     * @param username  用户名
     * @return
     */

    // 密钥
    public static String SECRET_KEY = "6u1qo2qlp-1vs3zr2rm+%971hv^s=tb2m0_y2^3bkjllsdib!8";

    public static String createToken(Long ttlMillis,String username){
        Long time = (ttlMillis==null||ttlMillis.longValue()==0)?1800000:ttlMillis;
        //获取当前的系统时间 + 过期时间
        Date date = new Date(System.currentTimeMillis()+time);
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create().withClaim("username",username)
                .withExpiresAt(date)
                .sign(algorithm);
    }
}
