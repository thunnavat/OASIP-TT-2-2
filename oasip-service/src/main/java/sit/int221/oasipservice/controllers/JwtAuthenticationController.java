package sit.int221.oasipservice.controllers;

import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sit.int221.oasipservice.config.JwtTokenUtil;
import sit.int221.oasipservice.dtos.JwtRequest;
import sit.int221.oasipservice.dtos.JwtResponse;
import sit.int221.oasipservice.services.JwtUserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;

    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

//    @RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
//    public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
//
//        // From the HttpRequest get the claims
//        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
//
//        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
//        String token = jwtUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
//        return ResponseEntity.ok(new AuthenticationResponse(token));
//    }
//
//    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
//        Map<String, Object> expectedMap = new HashMap<String, Object>();
//        for (Map.Entry<String, Object> entry : claims.entrySet()) {
//            expectedMap.put(entry.getKey(), entry.getValue());
//        }
//        return expectedMap;
//    }

}

//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import sit.int221.oasipservice.dtos.MatchDTO;
//import sit.int221.oasipservice.services.UserService;
//
//@RestController
//@RequestMapping("/api")
//public class JwtAuthenticationController {
//    private final UserService service;
//
//    public JwtAuthenticationController(UserService service) {
//        this.service = service;
//    }
//
//    @PostMapping("/match")
//    public void match(@RequestBody MatchDTO login) { service.match(login); }
//}
