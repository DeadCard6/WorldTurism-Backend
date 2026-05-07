package com.worldturism.spring.app.view.dto;

import com.worldturism.spring.app.model.ProviderProfile;

public record ProviderResponse(
		Long id,
		String businessName,
		String taxId,
		String description,
		String address,
		String category,
		String website,
		String logoUrl) {

	public static ProviderResponse from(ProviderProfile providerProfile) {
		if (providerProfile == null) {
			return null;
		}

		return new ProviderResponse(
				providerProfile.getId(),
				providerProfile.getBusinessName(),
				providerProfile.getTaxId(),
				providerProfile.getDescription(),
				providerProfile.getAddress(),
				providerProfile.getCategory(),
				providerProfile.getWebsite(),
				providerProfile.getLogoUrl());
	}
}
