package br.com.nossobancodigital.nossobanco.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class SecondStepRegistrationRequest {
	private static final String ZIP_CODE_REGEX = "[0-9]{7,8}";
	private static final String INVALID_ZIP_CODE = "CEP inválido.";

	@Range(min=1000000, max=99999999, message = INVALID_ZIP_CODE)
	private Integer zipCode;

	@NotBlank
	private String streetName;

	@NotBlank
	private String addressComplement;

	@NotBlank
	private String districtName;

	@NotBlank
	private String cityName;

	@NotBlank
	private String stateName;
}
