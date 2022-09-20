package sit.int221.oasipservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.oasipservice.entities.User;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByNameIgnoreCase(String name);

    User findByEmailIgnoreCase(String email);

}