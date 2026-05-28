package com.worldturism.spring.app.view.dto;

import com.worldturism.spring.app.model.Booking;
import com.worldturism.spring.app.model.BookingStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record BookingResponse(
		Long id,
		LocalDate bookingDate,
		Integer numPeople,
		BigDecimal totalPrice,
		BookingStatus status,
		Instant createdAt,
		Long businessId,
		String businessName,
		String businessPrice,
		Long providerUserId) {

	public static BookingResponse from(Booking booking) {
		return new BookingResponse(
				booking.getId(),
				booking.getBookingDate(),
				booking.getNumPeople(),
				booking.getTotalPrice(),
				booking.getStatus(),
				booking.getCreatedAt(),
				booking.getProviderBusiness().getId(),
				booking.getProviderBusiness().getBusinessName(),
				booking.getProviderBusiness().getPrice(),
				booking.getProviderBusiness().getUser().getId());
	}
}
