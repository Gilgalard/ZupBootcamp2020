package br.com.nossobancodigital.nossobanco.dto;

import br.com.nossobancodigital.nossobanco.validators.AgeMajority;
import br.com.nossobancodigital.nossobanco.validators.BrazilianDriverLicense;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;

@Setter
@Getter
public class FirstStepRegistrationRequest {
	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	@BrazilianDriverLicense
	private String driverLicenseNo;

	@NotNull
	@Past
	@AgeMajority
	private Date birthDate;
}