package com.worldturism.spring.app.view.dto;

import com.worldturism.spring.app.model.Review;
import java.time.Instant;

public record ReviewResponse(
		Long id,
		Integer rating,
		String comment,
		Instant createdAt,
		Long userId,
		String userName,
		Long serviceId,
		String serviceTitle) {

	public static ReviewResponse from(Review review) {
		return new ReviewResponse(
				review.getId(),
				review.getRating(),
				review.getComment(),
				review.getCreatedAt(),
				review.getUser().getId(),
				review.getUser().getName(),
				review.getService().getId(),
				review.getService().getTitle());
	}
}
