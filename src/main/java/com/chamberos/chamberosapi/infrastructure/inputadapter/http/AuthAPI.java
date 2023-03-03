package com.chamberos.chamberosapi.infrastructure.inputadapter.http;

import java.time.Instant;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import com.chamberos.chamberosapi.application.exception.ErrorResponseSingle;
import com.chamberos.chamberosapi.application.exception.TokenRefreshException;
import com.chamberos.chamberosapi.domain.model.RefreshToken;
import com.chamberos.chamberosapi.domain.model.User;
import com.chamberos.chamberosapi.domain.model.UserDetailsImpl;
import com.chamberos.chamberosapi.infrastructure.inputport.RefreshTokenInputPort;
import com.chamberos.chamberosapi.infrastructure.inputport.UserInputPort;
import com.chamberos.chamberosapi.infrastructure.security.jwt.JwtUtils;
import com.chamberos.chamberosapi.utils.HttpErrorMessages;

@RestController
@RequestMapping("/auth")
public class AuthAPI {

    @Value("${app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired(required = true)
    AuthenticationManager authenticationManager;

    @Autowired()
    UserInputPort userInputPort;

    @Autowired()
    RefreshTokenInputPort refreshTokenInputPort;

    @Autowired(required = true)
    JwtUtils jwtUtils;

    @PostMapping("refresh-token")
    public ResponseEntity<?> create(HttpServletRequest request) {
        String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);

        if (refreshToken == null || refreshToken.length() <= 0) {
            return new ResponseEntity<>(
                    HttpErrorMessages.REFRESH_TOKEN_EMPTY,
                    HttpStatus.BAD_REQUEST);
        }

        RefreshToken storedRefreshToken = refreshTokenInputPort.findByToken(refreshToken);

        if (storedRefreshToken == null) {
            return new ResponseEntity<>(
                    HttpErrorMessages.REFRESH_TOKEN_NOT_IN_DATABASE,
                    HttpStatus.UNAUTHORIZED);
        }

        if (storedRefreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenInputPort.deleteByToken(refreshToken);
            throw new TokenRefreshException(
                    storedRefreshToken.getToken(),
                    HttpErrorMessages.REFRESH_TOKEN_WAS_EXPIRED);
        }

        User user = storedRefreshToken.getUser();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new String(HttpErrorMessages.REFRESH_TOKEN_REFRESED_SUCCESSFULLY));
    }

    @PostMapping(value = "register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        User userEmail = userInputPort.findByEmail(user.getEmail());

        if (userEmail != null) {
            ErrorResponseSingle error = new ErrorResponseSingle(HttpErrorMessages.USER_EMAIL_ALREADY_EXIST);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        User userUsername = userInputPort.findByUsername(user.getUsername());

        if (userUsername != null) {
            ErrorResponseSingle error = new ErrorResponseSingle(HttpErrorMessages.USER_USERNAME_ALREADY_EXIST);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        User newUser = userInputPort.register(user);
        newUser.setPassword(null);
        
        return new ResponseEntity<>(
                newUser,
                HttpStatus.CREATED);
    }

    @PostMapping(value = "authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticate(@RequestBody User user) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User signedUser = new User();
        signedUser.setId(userDetails.getId());
        signedUser.setUsername(userDetails.getUsername());
        signedUser.setPassword(userDetails.getPassword());
        signedUser.setEmail(userDetails.getEmail());

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(signedUser);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(signedUser);

        RefreshToken storedToken = refreshTokenInputPort.save(refreshToken);

        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(storedToken.getToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(userDetails);
    }

    @PostMapping(value = "sign-out", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signOut(@RequestBody User user) {

        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principle.toString() != "anonymousUser") {
            String userId = ((UserDetailsImpl) principle).getId();
            refreshTokenInputPort.deleteByUserId(userId);
        }

        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
        ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new String(HttpErrorMessages.SIGN_OUT_MESSAGE));
    }
}
