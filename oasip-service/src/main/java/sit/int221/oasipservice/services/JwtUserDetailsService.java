package sit.int221.oasipservice.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.oasipservice.repositories.UserRepository;
import sit.int221.oasipservice.utils.Role;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles = null;
        sit.int221.oasipservice.entities.User userByEmail = userRepository.findByEmailIgnoreCase(email.trim());
        if (userByEmail != null) {
            roles = Arrays.asList(new SimpleGrantedAuthority(userByEmail.getRole().toString()));
            return new User(userByEmail.getEmail(), userByEmail.getPassword(), roles);
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}
