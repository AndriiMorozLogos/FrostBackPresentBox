package ua.lviv.frost.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.lviv.frost.entity.AppUser;
import ua.lviv.frost.entity.enumeration.Role;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    boolean existsByEmail(String email);

    Optional<AppUser> findByEmail(String email);

    List<AppUser> findByRole(Role role);

}
