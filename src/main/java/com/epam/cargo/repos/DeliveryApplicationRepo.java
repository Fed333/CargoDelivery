package com.epam.cargo.repos;

import com.epam.cargo.entity.DeliveryApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryApplicationRepo extends JpaRepository<DeliveryApplication, Long> {

}
