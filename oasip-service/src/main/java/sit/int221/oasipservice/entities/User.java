package sit.int221.oasipservice.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import sit.int221.oasipservice.utils.Role;

import javax.persistence.*;
import java.time.Instant;

@Getter @Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role = Role.student;

    @Column(name = "createdOn", nullable = false)
    @Generated(GenerationTime.INSERT)
    private Instant createdOn;

    @Column(name = "updatedOn", nullable = false)
    @Generated(GenerationTime.ALWAYS)
    private Instant updatedOn;

}