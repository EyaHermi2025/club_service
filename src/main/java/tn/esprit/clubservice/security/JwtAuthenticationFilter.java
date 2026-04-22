package tn.esprit.clubservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger filterLogger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String ROLE_PREFIX = "ROLE_";

    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = getJwtFromHeader(request);

        if (jwt != null && jwtUtils.validateToken(jwt)) {
            String userEmail = jwtUtils.extractUsername(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                List<GrantedAuthority> authorities = extractAuthorities(jwt);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userEmail, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private List<GrantedAuthority> extractAuthorities(String jwt) {
        List<GrantedAuthority> authorities = new java.util.ArrayList<>();
        Object roleClaim = getRoleClaim(jwt);

        filterLogger.debug("=== CLUB SECURITY DEBUG ===");
        filterLogger.debug("Extracted Role Claim: {}", roleClaim);

        if (roleClaim != null) {
            processRoleClaim(roleClaim, authorities);
        }

        filterLogger.debug("Final Authorities: {}", authorities);
        filterLogger.debug("======================");
        return authorities;
    }

    private Object getRoleClaim(String jwt) {
        return jwtUtils.extractClaim(jwt, claims -> {
            if (claims.get("role") != null) return claims.get("role");
            if (claims.get("roles") != null) return claims.get("roles");
            if (claims.get("Roles") != null) return claims.get("Roles");
            return claims.get("authorities");
        });
    }

    private void processRoleClaim(Object roleClaim, List<GrantedAuthority> authorities) {
        if (roleClaim instanceof String r) {
            String formattedRole = r.startsWith(ROLE_PREFIX) ? r : ROLE_PREFIX + r.toUpperCase();
            authorities.add(new SimpleGrantedAuthority(formattedRole));
        } else if (roleClaim instanceof java.util.Collection<?> roles) {
            roles.forEach(r -> {
                String roleStr = r.toString();
                String formattedRole = roleStr.startsWith(ROLE_PREFIX) ? roleStr : ROLE_PREFIX + roleStr.toUpperCase();
                authorities.add(new SimpleGrantedAuthority(formattedRole));
            });
        }
    }
}
