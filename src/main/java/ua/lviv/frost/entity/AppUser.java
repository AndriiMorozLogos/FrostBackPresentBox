package ua.lviv.frost.entity;

import lombok.*;
import lombok.experimental.Accessors;
import ua.lviv.frost.entity.enumeration.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public  class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Pattern(regexp = "\\d{10}")
    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "buyer", orphanRemoval = true)
    private Set<PresentOrder> orders = new HashSet<>();

}
