package tn.esprit.clubservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private JwtUtils jwtUtils;
    private String secret = "4n7p9r2t5v8y_B_E_H_KbPeShVmYq3t6w9z_C_F_J_NcQfTjWnZr4u7x_A_D_G_Ka";
    private SecretKey key;

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        ReflectionTestUtils.setField(jwtUtils, "secret", secret);
        jwtUtils.init();
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Test
    void testExtractUsername() {
        String token = Jwts.builder()
                .setSubject("test@example.com")
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        assertEquals("test@example.com", jwtUtils.extractUsername(token));
    }

    @Test
    void testValidateToken_Success() {
        String token = Jwts.builder()
                .setSubject("user")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        assertTrue(jwtUtils.validateToken(token));
    }

    @Test
    void testValidateToken_Failure() {
        String invalidToken = "invalid.token.here";
        assertFalse(jwtUtils.validateToken(invalidToken));
    }

    @Test
    void testExtractClaim() {
        String token = Jwts.builder()
                .setSubject("user")
                .setIssuedAt(new Date())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        Date issuedAt = jwtUtils.extractClaim(token, Claims::getIssuedAt);
        assertNotNull(issuedAt);
    }
}
