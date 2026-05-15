package com.worldturism.spring.app.repository;

import com.worldturism.spring.app.model.Booking;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findByUserIdOrderByCreatedAtDesc(Long userId);
}
