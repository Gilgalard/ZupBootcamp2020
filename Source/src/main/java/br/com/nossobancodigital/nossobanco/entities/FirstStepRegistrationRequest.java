package br.com.nossobancodigital.nossobanco.entities;

import br.com.nossobancodigital.nossobanco.validators.AgeMajority;
import br.com.nossobancodigital.nossobanco.validators.BrazilianDriverLicense;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;

@Setter
@Getter
public class FirstStepRegistrationRequest {
	private final String FIELD_REQUIRED = "required";
	private final String FIELD_INVALID = "invalid";

	@NotEmpty(message = FIELD_REQUIRED)
	private String firstName;

	@NotEmpty(message = FIELD_REQUIRED)
	private String lastName;

	@NotEmpty(message = FIELD_REQUIRED)
	@Email(message = FIELD_INVALID)
	private String email;

	@NotEmpty(message = FIELD_REQUIRED)
	@BrazilianDriverLicense(message = FIELD_INVALID)
	private String driverLicenseNo;

	@NotNull(message = FIELD_REQUIRED)
	@Past(message = FIELD_INVALID)
	@AgeMajority(message = FIELD_INVALID)
	private Date birthDate;
}