package com.enigma.wmb_api.security;

import com.enigma.wmb_api.dto.response.JwtClaims;
import com.enigma.wmb_api.entity.MUserAccount;
import com.enigma.wmb_api.service.JwtService;
import com.enigma.wmb_api.service.UserAccountService;
import com.enigma.wmb_api.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserAccountService userAccountService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // validasi token
        try {
            String bearerToken = request.getHeader("authorization");

            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                // server akan menyimpan informasi user yang terverifikasi
                // ke storage security context selama permintaan http selesai
                JwtClaims claims = jwtService.getClaimsByToken(bearerToken);
                MUserAccount userAccount = userAccountService.getByUserId(claims.getUserAccountId());

                // verifikasi login menggunakan username dan roles
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userAccount.getUsername(),
                        null,
                        userAccount.getAuthorities()
                );

                // mengambil info tambahan berupa ip address dll
                authentication.setDetails(new WebAuthenticationDetails(request));

                // menyimpan informasi
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("cannot set user authentication: {}", e.getMessage());
        }

        // meneruskan request ke controller
        filterChain.doFilter(request, response);
    }
}
