package sit.int221.oasipservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity @Table(name = "eventcategoryowner")
public class EventCategoryOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventCategoryOwnerId", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "eventCategoryId", nullable = false)
    private EventCategory eventCategory;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

}
