package com.worldturism.spring.app.service;

import com.worldturism.spring.app.model.AppUser;
import com.worldturism.spring.app.model.Booking;
import com.worldturism.spring.app.model.Role;
import com.worldturism.spring.app.model.ServiceStatus;
import com.worldturism.spring.app.model.TourService;
import com.worldturism.spring.app.repository.BookingRepository;
import com.worldturism.spring.app.repository.TourServiceRepository;
import com.worldturism.spring.app.view.dto.BookingRequest;
import com.worldturism.spring.app.view.dto.BookingResponse;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

	private final BookingRepository bookingRepository;
	private final TourServiceRepository tourServiceRepository;

	public BookingService(
			BookingRepository bookingRepository,
			TourServiceRepository tourServiceRepository) {
		this.bookingRepository = bookingRepository;
		this.tourServiceRepository = tourServiceRepository;
	}

	@Transactional
	public BookingResponse create(AppUser user, BookingRequest request) {
		validateUser(user);

		TourService service = tourServiceRepository.findById(request.serviceId())
				.orElseThrow(() -> new IllegalArgumentException("El servicio no existe."));
		if (service.getStatus() == ServiceStatus.REJECTED || service.getStatus() == ServiceStatus.INACTIVE) {
			throw new IllegalArgumentException("El servicio no esta disponible para reservas.");
		}

		Booking booking = new Booking();
		booking.setUser(user);
		booking.setService(service);
		booking.setBookingDate(request.bookingDate());
		booking.setNumPeople(request.numPeople());
		booking.setTotalPrice(service.getPrice().multiply(BigDecimal.valueOf(request.numPeople())));

		Booking savedBooking = bookingRepository.save(booking);
		return BookingResponse.from(savedBooking);
	}

	@Transactional(readOnly = true)
	public List<BookingResponse> listMyBookings(AppUser user) {
		validateUser(user);

		return bookingRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
				.stream()
				.map(BookingResponse::from)
				.toList();
	}

	private void validateUser(AppUser user) {
		if (user == null) {
			throw new IllegalArgumentException("Usuario no autenticado.");
		}
		if (user.getRole() != Role.USER) {
			throw new IllegalArgumentException("Solo los usuarios pueden crear o consultar sus reservas.");
		}
	}
}
