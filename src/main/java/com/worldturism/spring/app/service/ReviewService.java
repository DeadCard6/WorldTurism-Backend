package com.worldturism.spring.app.service;

import com.worldturism.spring.app.model.AppUser;
import com.worldturism.spring.app.model.Review;
import com.worldturism.spring.app.model.Role;
import com.worldturism.spring.app.model.TourService;
import com.worldturism.spring.app.repository.ReviewRepository;
import com.worldturism.spring.app.repository.SavedServiceRepository;
import com.worldturism.spring.app.repository.TourServiceRepository;
import com.worldturism.spring.app.view.dto.ReviewRequest;
import com.worldturism.spring.app.view.dto.ReviewResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final TourServiceRepository tourServiceRepository;
	private final SavedServiceRepository savedServiceRepository;

	public ReviewService(
			ReviewRepository reviewRepository,
			TourServiceRepository tourServiceRepository,
			SavedServiceRepository savedServiceRepository) {
		this.reviewRepository = reviewRepository;
		this.tourServiceRepository = tourServiceRepository;
		this.savedServiceRepository = savedServiceRepository;
	}

	@Transactional
	public ReviewResponse create(AppUser user, ReviewRequest request) {
		validateUser(user);

		TourService service = tourServiceRepository.findById(request.serviceId())
				.orElseThrow(() -> new IllegalArgumentException("El servicio no existe."));
		if (!savedServiceRepository.existsByUserIdAndServiceId(user.getId(), service.getId())) {
			throw new IllegalArgumentException("Debes adquirir el servicio antes de dejar una resena.");
		}
		if (reviewRepository.existsByUserIdAndServiceId(user.getId(), service.getId())) {
			throw new IllegalArgumentException("Ya dejaste una resena para este servicio.");
		}

		Review review = new Review();
		review.setUser(user);
		review.setService(service);
		review.setRating(request.rating());
		review.setComment(request.comment());

		updateServiceRating(service, request.rating());
		return ReviewResponse.from(reviewRepository.save(review));
	}

	@Transactional(readOnly = true)
	public List<ReviewResponse> listByService(Long serviceId) {
		if (!tourServiceRepository.existsById(serviceId)) {
			throw new IllegalArgumentException("El servicio no existe.");
		}

		return reviewRepository.findByServiceIdOrderByCreatedAtDesc(serviceId)
				.stream()
				.map(ReviewResponse::from)
				.toList();
	}

	@Transactional(readOnly = true)
	public List<ReviewResponse> listMyReviews(AppUser user) {
		validateUser(user);

		return reviewRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
				.stream()
				.map(ReviewResponse::from)
				.toList();
	}

	private void updateServiceRating(TourService service, Integer rating) {
		int currentTotal = service.getTotalReviews() == null ? 0 : service.getTotalReviews();
		float currentAverage = service.getAvgRating() == null ? 0.0f : service.getAvgRating();
		int newTotal = currentTotal + 1;
		float newAverage = ((currentAverage * currentTotal) + rating) / newTotal;

		service.setTotalReviews(newTotal);
		service.setAvgRating(newAverage);
	}

	private void validateUser(AppUser user) {
		if (user == null) {
			throw new IllegalArgumentException("Usuario no autenticado.");
		}
		if (user.getRole() != Role.USER) {
			throw new IllegalArgumentException("Solo los usuarios pueden dejar resenas.");
		}
	}
}
