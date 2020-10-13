package br.com.nossobancodigital.nossobanco.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class SecondStepRegistrationRequest {
	private static final String ZIP_CODE_REGEX = "[0-9]{7,8}";
	private final String FIELD_REQUIRED = "required";
	private final String FIELD_INVALID = "invalid";

	@Range(min=1000000, max=99999999, message = FIELD_INVALID)
	private Integer zipCode;

	@NotBlank(message = FIELD_REQUIRED)
	private String streetName;

	@NotBlank(message = FIELD_REQUIRED)
	private String addressComplement;

	@NotBlank(message = FIELD_REQUIRED)
	private String districtName;

	@NotBlank(message = FIELD_REQUIRED)
	private String cityName;

	@NotBlank(message = FIELD_REQUIRED)
	private String stateName;
}
