package tn.esprit.clubservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger filterLogger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        if (jwtUtils.validateToken(jwt)) {
            userEmail = jwtUtils.extractUsername(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Robust Role Extraction
                java.util.List<org.springframework.security.core.GrantedAuthority> authorities = new java.util.ArrayList<>();
                
                Object roleClaim = jwtUtils.extractClaim(jwt, claims -> {
                    if (claims.get("role") != null) return claims.get("role");
                    if (claims.get("roles") != null) return claims.get("roles");
                    if (claims.get("Roles") != null) return claims.get("Roles");
                    if (claims.get("authorities") != null) return claims.get("authorities");
                    return null;
                });

                filterLogger.debug("=== CLUB SECURITY DEBUG ===");
                filterLogger.debug("User: {}", userEmail);
                filterLogger.debug("Extracted Role Claim: {}", roleClaim);

                if (roleClaim != null) {
                    if (roleClaim instanceof String) {
                        String r = (String) roleClaim;
                        if (!r.startsWith("ROLE_")) r = "ROLE_" + r.toUpperCase();
                        authorities.add(new org.springframework.security.core.authority.SimpleGrantedAuthority(r));
                    } else if (roleClaim instanceof java.util.Collection) {
                        ((java.util.Collection<?>) roleClaim).forEach(r -> {
                            String roleStr = r.toString();
                            if (!roleStr.startsWith("ROLE_")) roleStr = "ROLE_" + roleStr.toUpperCase();
                            authorities.add(new org.springframework.security.core.authority.SimpleGrantedAuthority(roleStr));
                        });
                    }
                }
                
                filterLogger.debug("Final Authorities: {}", authorities);
                filterLogger.debug("======================");

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userEmail, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
