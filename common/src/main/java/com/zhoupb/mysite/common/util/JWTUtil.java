package com.zhoupb.mysite.common.util;

import com.zhoupb.mysite.common.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

public class JWTUtil {

	private static final String PAYLOAD_ACCOUNT_ID_KEY = "i";

	// 毫秒
	private static final long EXPIRATION_TIME = Duration.ofDays(30).toMillis();

	private static final Key key = Keys.hmacShaKeyFor("r3vol1zj7#ld5muhelv$i^1h956o%vkmc+24mey2b&&w2--!1s".getBytes());

//	public JWTUtil(@Value("${app.jwt-secret-key}") String secretKey) {
//		key = Keys.hmacShaKeyFor(secretKey.getBytes());
//	}

	public static String generateToken(long id) {
		if (id <= 0)
			throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "服务器内部错误");
		Date now = new Date();
		Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
		return Jwts.builder().setClaims(Map.of(PAYLOAD_ACCOUNT_ID_KEY, id)).setIssuedAt(now).setExpiration(expiration)
				.signWith(key).compact();
	}

	/**
	 * 鉴权 返回用户id
	 * @param token JWT token
	 * @return accountId
	 */
	public static long validateToken(String token) {
		if ( !StringUtils.hasText(token) )
			throw new CustomException(HttpStatus.UNAUTHORIZED, "先登录吧");
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
			return claims.get(PAYLOAD_ACCOUNT_ID_KEY, Long.class);
		} catch (Exception e) {
			throw new CustomException(HttpStatus.UNAUTHORIZED, "严重token失败");
		}
	}

}