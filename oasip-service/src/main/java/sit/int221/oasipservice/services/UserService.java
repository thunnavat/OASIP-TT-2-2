package sit.int221.oasipservice.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import sit.int221.oasipservice.dtos.CreateUserDTO;
import sit.int221.oasipservice.dtos.UpdateUserDTO;
import sit.int221.oasipservice.entities.User;
import sit.int221.oasipservice.repositories.UserRepository;


import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Argon2PasswordEncoder encoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, Argon2PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }

    public List<User> getUsers() { return userRepository.findAll(Sort.by("name")); }

    public User create(CreateUserDTO newUser){
        User userByName = userRepository.findByNameIgnoreCase(newUser.getName().trim());
        if (userByName != null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is repeat");
        User userByEmail = userRepository.findByEmailIgnoreCase(newUser.getEmail().trim());
        if (userByEmail != null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is repeat");

        String hashPassword = encoder.encode(newUser.getPassword());

        User user = modelMapper.map(newUser, User.class);
        user.setName(newUser.getName().trim());
        user.setEmail(newUser.getEmail().trim());
        user.setPassword(hashPassword);
        return userRepository.saveAndFlush(user);
    }

    public void delete(Integer userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User id " + userId + "Does not exist"));
        userRepository.deleteById(userId);
    }

    public User update(UpdateUserDTO updateUser, Integer userId) {
        User userByName = userRepository.findByNameIgnoreCase(updateUser.getName().trim());
        if (userByName != null && userByName.getId() != userId) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is repeat");
        User userByEmail = userRepository.findByEmailIgnoreCase(updateUser.getEmail().trim());
        if (userByEmail != null && userByEmail.getId() != userId) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is repeat");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event CategoryId " + userId + "Does not exist"));

        user.setName(updateUser.getName().trim());
        user.setEmail(updateUser.getEmail().trim());
        user.setRole(updateUser.getRole());

        return userRepository.saveAndFlush(user);
    }

//    public void match(JwtRequest login) {
//        User userByUsername = userRepository.findByEmailIgnoreCase(login.getUsername().trim());
//        if (userByUsername == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        Boolean matchStatus = encoder.matches(login.getPassword(), userByUsername.getPassword());
//        if (matchStatus == false) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
//    }

}
