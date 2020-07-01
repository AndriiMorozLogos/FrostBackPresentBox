package ua.lviv.frost.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.lviv.frost.entity.PresentBox;

import java.util.Optional;


@Repository
public interface PresentBoxRepository extends JpaRepository<PresentBox, Integer> {

    Optional<PresentBox> findById(Integer presentBoxId);
}
