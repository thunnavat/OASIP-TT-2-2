package sit.int221.oasipservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.oasipservice.entities.EventCategoryOwner;
import sit.int221.oasipservice.entities.User;

import java.util.List;

public interface EventCategoryOwnerRepository extends JpaRepository<EventCategoryOwner, Integer> {
    List<EventCategoryOwner> findAllByUser(User user);
}
