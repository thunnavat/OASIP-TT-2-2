package sit.int221.oasipservice.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import sit.int221.oasipservice.dtos.CreateUserDTO;
import sit.int221.oasipservice.entities.User;
import sit.int221.oasipservice.repositories.UserRepository;


import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<User> getUsers() { return userRepository.findAll(Sort.by("name")); }

    public User create(CreateUserDTO newUser){
        User userByName = userRepository.findByNameIgnoreCase(newUser.getName().trim());
        if (userByName != null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is repeat");
        User userByEmail = userRepository.findByEmailIgnoreCase(newUser.getEmail().trim());
        if (userByEmail != null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is repeat");

        User user = modelMapper.map(newUser, User.class);
        user.setName(user.getName().trim());
        user.setEmail(user.getEmail().trim());
        return userRepository.saveAndFlush(user);
    }

    public void delete(Integer userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User id " + userId + "Does not exist"));
        userRepository.deleteById(userId);
    }

}
