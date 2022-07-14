package cn.raths.basic.jwt;

import cn.raths.user.domain.User;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.UUID;

/**
 * JWT 密钥的解析和加密 工具类
 */
public class JwtUtils {

    private static final String JWT_PAYLOAD_USER_KEY = "user";


    private static String createJTI() {
        return new String(Base64.getEncoder()
        	.encode(UUID.randomUUID().toString().getBytes()));
    }
    /**
     * 私钥加密token
     *
     * @param loginData   载荷中的数据
     * @param privateKey 私钥
     * @param expire     过期时间，单位分钟
     * @return JWT
     */
    public static String generateTokenExpireInMinutes(Object loginData, PrivateKey privateKey, int expire) {
        return Jwts.builder()
                .claim(JWT_PAYLOAD_USER_KEY, JSONObject.toJSONString(loginData))
                .setId(createJTI())
                //当前时间往后加多少分钟
                .setExpiration(DateTime.now().plusMinutes(expire).toDate())
                .signWith(SignatureAlgorithm.RS256,privateKey)
                .compact();

    }

    /**
     * 私钥加密token
     *
     * @param loginData   载荷中的数据
     * @param privateKey 私钥
     * @param expire     过期时间，单位秒
     * @return JWT
     */
    public static String generateTokenExpireInSeconds(Object loginData, PrivateKey privateKey, int expire) {
        return Jwts.builder()
                .claim(JWT_PAYLOAD_USER_KEY, JSONObject.toJSONString(loginData))
                .setId(createJTI())
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.RS256,privateKey)
                .compact();
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥
     * @return Jws<Claims>
     */
    private static Jws<Claims> parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }


    /**
     * 获取token中的用户信息
     *
     * @param token  用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     */
    public static <T> Payload<T> getInfoFromToken(String token, PublicKey publicKey, Class<T> userType) {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload<>();
        claims.setId(body.getId());
        T t = JSONObject.parseObject(body.get(JWT_PAYLOAD_USER_KEY).toString(),userType);
        claims.setUserInfo(t);
        claims.setExpiration(body.getExpiration());
        return claims;
    }

    /**
     * 获取token中的载荷信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     */
    public static <T> Payload<T> getInfoFromToken(String token, PublicKey publicKey) {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload<>();
        claims.setId(body.getId());
        claims.setExpiration(body.getExpiration());
        return claims;
    }


    /**
    * @Title: main
    * @Description: 测试JWT加密和解密
    * @Author: Mr.She
    * @Version: 1.0
    * @Date:  2022/6/30 11:11
    * @Parameters: [java.lang.String[]]
    * @Return void
    */
    public static void main(String[] args) throws Exception {
//        String a = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCZMl12eVdfwrkje1i9BEbWH3xIzSL4fwVv2wwmpKZHuFlHPeDDJCfpSd977kBlygqCd4mYw/yCdQw60h7Au0zJbLEGLKD7ZdEJUthn2iIGWCfT2xIpFsip0mNYtGkzYr5McVuIJUEZltar94t1rOPciD1giafk64++X7pqEp7NGCTf9ux8DGcGotuddJWciTro62LPdJDzlh0o4+L+Lw+LCXk29k+3iwKiVhbZ/qRWmkedQwjAKzMrXISj27s8DSeZIAE+98Xosq40JVB9N+mrHesbUz+eIk/VWXxHHBpFMEW1hOm7erexkPJhhRYmAIiHoBijEfmDVBHjMgMA7ggNAgMBAAECggEABIyETfJIb+V+TdlpxyMLX2xL6nV85lgPN0UP1U3qShLlQ8JInELElJ7G7g0jCGK5cSpPgaPAKbeEB3MZyqtFGm4Jy58joJNjY518I4lMnVecxyOoJGQyzSgcHxQsU4RJFkf/acmBZ1sdQLn3ACWEFpZNDELKI3gDKqn5BRFMedaDsyZ3u9O7SPpKSbpqcPEa+Db0iqMb/04hGSIxKdp2blIyB1ux56fcONU+A+365vrSiDylBmcjGrSm0nq3QoOdEp0CscNcg9UohR+B+2S70pB+jOAWok9Z+Qz1F5JGa4K67KcBI7u+RFHfaoz3JLFbzs68NHzidkQU3vBnivA7gQKBgQDxqg5iHQ2rDXvS+3t558iYzkZZcF/4dxFpad1oP82Kyv8d8CxNykleREHcEzAXR2Zl1gaYOFFskQmQ+bSe0Q8iZ5rmvSCG3ufvKlzwcrsIR2rbk94oz/3B6Ll8HOWZalTrLrTjUnNgv+M4u1I/di9dfr9KYizzjk2sktX1K+JWfQKBgQCiSNRtp2s/9UME8mPn9YkhRc233OPWhjyPAjXMkB+0iC4Xt7Js1RTuNV5QMmq5qSeUjKwXuhUJ+mOW50Dt8ypGrSrzM/RqB7rp4xpRc6KbfwVD+D6w7oGn6y/mT6gqUmrzeJ1F+/zQwLkSySQlIv2RGNsltMexnNBPV48IZLrc0QKBgGlQQpRfNOWmKp/3GAg4CiMNQXsYjbopqVzW1bXomBoz/Jl/YM9FqFN8S3cspXh/lRAsBROw90roqdKT/zJ2yLGllMuY/+PGoCyJuZbPaNJizR6Dgf0nJt6qHKsJrMT+oaMb55hpBf7ToKHIgD9Hsbx8z8AKqKw7B359PuBpD2nBAoGAOD4XaR1hvRvKK1e6KjTdXqurfTJPwBRrvoBcltXINzAPEWc3riI3C8txMUgX1tp5/jC2n8585iViCHWC+jSRDUeMoBb8eRbOv6pWf64cx9lAwOqzB2zRgJmw0xf+RxSjPlgp4YxQdSSlv434OeqjiHrd0HaT0slvJGBLEPSgElECgYEA0X3dqrAgrjPitX2OA3OaTWgMqhYebIqpV/H8jGOOmoLFCieNvWfBeeolp6H0gnD4vi2NccJ1y6pkVnLQlJUDGr/l0BwRMoNLDw+SJmb2qz69oxx7IjOYGSAWbtziqCVt0uwqMk7U5JFslxbyHhBOhAMnOrWcjB8a0tSFk0ouA5Q=";
//        System.out.println(a.length());
        // 1 获取token
        PrivateKey privateKey = RsaUtils.getPrivateKey(JwtUtils.class.getClassLoader().getResource("jwt/pethome_auth_rsa.pri").getFile());
        System.out.println(privateKey);
        User user = new User();
        user.setUsername("测试");
        String token = generateTokenExpireInMinutes(user, privateKey, 1);
        System.out.println(token);

        // 2 解析token里面内容
        PublicKey publicKey = RsaUtils.getPublicKey(JwtUtils.class.getClassLoader().getResource("jwt/pethome_auth_rsa.pub").getFile());
        Payload<User> payload = getInfoFromToken(token, publicKey, User.class);
        System.out.println(payload);
        Thread.sleep(61000); //超时后继续解析
        payload = getInfoFromToken(token, publicKey, User.class);
        System.out.println(payload);

    }

}