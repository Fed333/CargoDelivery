package com.epam.cargo.repos;

import com.epam.cargo.entity.DeliveryApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository of fetching DeliveredApplication objects from database.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface DeliveryApplicationRepo extends JpaRepository<DeliveryApplication, Long> {

    List<DeliveryApplication> findAllByUserId(Long userId);

    Page<DeliveryApplication> findAllByUserId(Long userId, Pageable pageable);
}
