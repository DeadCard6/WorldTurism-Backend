package com.worldturism.spring.app.view.dto;

public record ProviderRegisterRequest(
		String businessName,
		String taxId,
		String description,
		String address,
		String category,
		String website,
		String logoUrl) {
}
