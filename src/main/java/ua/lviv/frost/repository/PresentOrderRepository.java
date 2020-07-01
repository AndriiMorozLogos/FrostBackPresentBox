package ua.lviv.frost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.lviv.frost.entity.AppUser;
import ua.lviv.frost.entity.PresentOrder;

import java.util.List;
import java.util.Optional;


@Repository
public interface PresentOrderRepository extends JpaRepository<PresentOrder, Integer> {

    Optional<PresentOrder> findByIdAndBuyer(Integer presentOrderId, AppUser buyer);

    List<PresentOrder> findByBuyer(AppUser buyer);
}
