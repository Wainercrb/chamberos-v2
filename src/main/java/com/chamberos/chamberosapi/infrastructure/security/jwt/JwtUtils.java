package com.chamberos.chamberosapi.infrastructure.security.jwt;

import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import com.chamberos.chamberosapi.domain.model.User;
import com.chamberos.chamberosapi.domain.model.UserDetailsImpl;
import com.chamberos.chamberosapi.utils.AppConstants;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {

  @Value("${app.jwtSecret}")
  private String jwtSecret;

  @Value("${app.jwtExpirationMs}")
  private int jwtExpirationMs;

  @Value("${app.jwtCookieName}")
  private String jwtCookie;

  @Value("${app.jwtRefreshCookieName}")
  private String jwtRefreshCookie;

  public String getJwtRefreshFromCookies(HttpServletRequest request) {
    return getCookieValueByName(request, jwtRefreshCookie);
  }

  public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
    String jwt = generateTokenFromUsername(userPrincipal.getUsername());
    return generateCookie(jwtCookie, jwt, AppConstants.ROOT_PATH);
  }

  public ResponseCookie generateJwtCookie(User user) {
    String jwt = generateTokenFromUsername(user.getUsername());
    return generateCookie(jwtCookie, jwt, AppConstants.ROOT_PATH);
  }

  public String generateTokenFromUsername(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String generateTokenFromEmail(String email) {
    return Jwts.builder()
        .setSubject(email)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public ResponseCookie generateRefreshJwtCookie(String refreshToken) {
    return generateCookie(jwtRefreshCookie, refreshToken, AppConstants.REFRESH_TOKEN_PATH);
  }

  private ResponseCookie generateCookie(String name, String value, String path) {
    ResponseCookie cookie = ResponseCookie
        .from(name, value)
        .path(path)
        .sameSite("None")
        .secure(true)
        .maxAge(24 * 60 * 60)
        .httpOnly(true)
        .build();

    return cookie;
  }

  private String getCookieValueByName(HttpServletRequest request, String name) {

    Cookie cookie = WebUtils.getCookie(request, name);
    if (cookie != null) {
      return cookie.getValue();
    }
    return null;
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      // logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      // logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      // logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      // logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      // logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public String getJwtFromCookies(HttpServletRequest request) {
    return getCookieValueByName(request, jwtCookie);
  }

  public ResponseCookie getCleanJwtCookie() {
    ResponseCookie cookie = ResponseCookie
        .from(jwtCookie, null)
        .path(AppConstants.AUTH_PATH)
        .build();

    return cookie;
  }

  public ResponseCookie getCleanJwtRefreshCookie() {
    ResponseCookie cookie = ResponseCookie
        .from(jwtRefreshCookie, null)
        .path(AppConstants.REFRESH_TOKEN_PATH)
        .build();

    return cookie;
  }
}