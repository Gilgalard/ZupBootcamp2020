package br.com.nossobancodigital.nossobanco.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ThirdStepRegistrationRequest {
	private final String EMPTY_FIELD = "Empty";

	// Here we could pass the received image through an ML service, who will return if the driver license number
	// filled in the first step is the same as the image, for example, and if the image is like a brazilian driver
	// license.
	@NotBlank(message = EMPTY_FIELD)
	private String driverLicenseFrontImage;

	@NotBlank(message = EMPTY_FIELD)
	private String driverLicenseBackImage;
}
