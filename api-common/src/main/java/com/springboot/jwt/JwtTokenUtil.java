package com.springboot.jwt;

import com.springboot.common.AES128Util;
import com.springboot.common.SixCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaolei
 * createTime 2018年9月13日 下午3:54:59
 */
public class JwtTokenUtil{
	private static final String CLAIM_KEY_USERNAME = "sub";
	private static final String CLAIM_KEY_CREATED = "created";

	/**
	 * 生成jwt token
	 * @param key 加密的内容
	 * @return jwt
	 */
	public static String generateToken(String key) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, key);
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims,SystemDefines.JWT_EXPIRATION);
	}

	private static String generateToken(Map<String, Object> claims, Long expiration) {
		return Jwts.builder()
				.setClaims(claims)                                                       // 自定义属性
				.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)) // 过期时间
				.setIssuedAt(new Date(System.currentTimeMillis()))                       //当前时间
				.signWith(SignatureAlgorithm.HS512, SystemDefines.JWT_SECRET)            // 签名算法以及密匙
				.compact();
	}
	/**
	 * 判断token是否可以刷新
	 * @param token
	 * @return 刷新后的jwt
	 */
	public static Boolean canTokenBeRefreshed(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(SystemDefines.JWT_SECRET)
					.parseClaimsJws(token)
					.getBody();
			final Date exp = claims.getExpiration();
			if (exp.before(new Date(System.currentTimeMillis()))) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 刷新token
	 * @param token
	 * @return 刷新后的jwt
	 */
	static String refreshToken(String token) {
//		claims.put(CLAIM_KEY_CREATED, new Date());
		String refreshedToken;
		try {
			final Claims claims = Jwts.parser()
					.setSigningKey(SystemDefines.JWT_SECRET)
					.parseClaimsJws(token)
					.getBody();
			refreshedToken = generateToken(claims, SystemDefines.JWT_EXPIRATION);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
//		return generateToken(claims, expiration);
	}
	/**
	 * Token
	 *
	 * @return Token中包含的用户名
	 */
	public static String getUsername(String Token) {
		try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SystemDefines.JWT_SECRET) //私钥
                    .parseClaimsJws(Token) //加密字符窜
                    .getBody();
			return claims.get("sub").toString();
		} catch (Exception e) {
			return "";
		}
	}
    /**
     * 校验 Token 是否正确
     *
     * @return 是否正确
     */
    public static boolean verify(String Token, String username){

    	try{
			Claims claims = Jwts.parser()
					.setSigningKey(SystemDefines.JWT_SECRET)
					.parseClaimsJws(Token).getBody();
			return claims.get("sub").equals(username);
		}catch (Exception e)
		{
//			e.printStackTrace();
		}
		return false;
    }
	public static void executeStoredProcedure(Connection con) {
		CallableStatement cstmt = null;
		try {
			cstmt = con.prepareCall("{call dbo.PRO_GET_NEW_PZH(?,?,?,?)}");
			cstmt.setString("YWBH", "CG");
			cstmt.setString("TZRQ", String.valueOf(System.currentTimeMillis()));
			cstmt.setString("BMBH","K010001");
			cstmt.registerOutParameter("RPZH", Types.VARCHAR);
			cstmt.execute();
			System.out.println(cstmt.getString(4));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    public static void main(String[] args) {
        String token=generateToken("admin");
//        String token = "1112312312321er3erewreyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzNDM0MzQzNDM0MzQzNCIsImNyZWF0ZWQiOjE1NzEzOTQ0MTc4OTYsImV4cCI6MTU3MTk5OTIxOH0.vob8TCLco3j66pN64ZzIeBI4kHfu5YqTsU1vfQ1JUCJTTAuwuf92g6NIdygGIUn55YFocSU_uJCP8iNI-wc9_g";
        System.out.println("获取的token是："+token);
        String username=getUsername(token);
		System.out.println("username："+username);
        System.out.println(JwtTokenUtil.verify(token,username));
//		String sixCode=SixCode.getStringRandom(5);
//		System.out.println(sixCode);

	}
}
