package sit.int221.oasipservice.services;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.oasipservice.repositories.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        sit.int221.oasipservice.entities.User userByEmail = userRepository.findByEmailIgnoreCase(email.trim());
        if (userByEmail != null) {
            return new User(userByEmail.getEmail(), userByEmail.getPassword(),
                    new ArrayList<>());
        } else {
//            throw new UsernameNotFoundException("User not found with email: " + email);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email);
        }
    }
}
