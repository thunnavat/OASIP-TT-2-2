package sit.int221.oasipservice.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import sit.int221.oasipservice.services.JwtUserDetailsService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String bearerToken = request.getHeader("Authorization");
        String jwtToken = null;

        // Once we get the token validate it.
        try {
            // JWT Token is in the form "Bearer token". Remove Bearer word and get
            // only the Token
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                jwtToken = bearerToken.substring(7);
            }

            if (jwtToken != null && jwtTokenUtil.validateToken(jwtToken)) {

                UserDetails userDetails = new User(jwtTokenUtil.getEmailFromToken(jwtToken),
                        "", jwtTokenUtil.getRoleFromToken(jwtToken));

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            } else {
                System.out.println("Cannot set the Security Context");
            }

        } catch (ExpiredJwtException ex) {
            String isRefreshToken = request.getHeader("isRefreshToken");
            // allow for Refresh Token creation if following conditions are true.
            if (isRefreshToken != null && isRefreshToken.equals("true")) {
                request.setAttribute("claims", ex.getClaims());
            } else {
                request.setAttribute("exception", ex);
            }
        } catch (BadCredentialsException ex) {
            request.setAttribute("exception", ex);
            throw ex;
        }


        chain.doFilter(request, response);
    }

}
