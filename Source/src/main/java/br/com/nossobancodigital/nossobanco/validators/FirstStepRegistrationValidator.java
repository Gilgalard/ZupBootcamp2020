package br.com.nossobancodigital.nossobanco.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

import static java.util.Calendar.*;

import java.time.LocalDate;
import java.util.Date;

import br.com.nossobancodigital.nossobanco.entities.FirstStepRegistrationEntity;
import br.com.nossobancodigital.nossobanco.repositories.ProposalRepository;
import br.com.nossobancodigital.nossobanco.responses.FirstStepRegistrationResponseEntity;
import br.com.nossobancodigital.nossobanco.responses.RegistrationErrorEntity;

@Component
public class FirstStepRegistrationValidator {
	private static final Integer MAJORITY_AGE = 18;
	private static final String DRIVER_LICENSE_NO_REGEX = "[0-9]{11,11}";

	// Validation errors constants.
	private final static String FIRST_NAME_FIELD_NAME = "firstName";
	private final static String LAST_NAME_FIELD_NAME = "lastName";
	private final static String EMAIL_FIELD_NAME = "email";
	private final static String BIRTH_DATE_FIELD_NAME = "birthDate";
	private final static String DRIVER_LICENSE_NO_FIELD_NAME = "driverLicenseNo";
	private final static String EMPTY_FIELD_DESCRIPTION = "O campo não pode ser vazio.";
	private final static String INVALID_FORMAT_DESCRIPTION = "Formato inválido.";
	private final static String INVALID_DATE_DESCRIPTION = "Data inválida.";
	private final static String ALREADY_EXISTS_DESCRIPTION = "Já existe cadastrado.";
	
	@Autowired
	private ProposalRepository proposalRepository;
	
	private RegistrationErrorEntity createError(String fieldName, String description) {
		RegistrationErrorEntity error = new RegistrationErrorEntity();
		error.setFieldName(fieldName);
		error.setDescription(description);
		
		return error;
	}
	
	private void registerError(FirstStepRegistrationResponseEntity results, String fieldName, String errorDescription) {
		results.getErrors().add(createError(fieldName, errorDescription));
	}
	
	public static int getDiffYears(Date first, Date last) {
	    Calendar a = getCalendar(first);
	    Calendar b = getCalendar(last);
	    int diff = b.get(YEAR) - a.get(YEAR);
	    if (a.get(MONTH) > b.get(MONTH) || 
	        (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
	        diff--;
	    }
	    return diff;
	}

	public static Calendar getCalendar(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    return cal;
	}
	
	private Boolean validateFirstName(FirstStepRegistrationEntity data, FirstStepRegistrationResponseEntity result) {
		Boolean validationResult = !StringUtils.isEmpty(data.getFirstName());
				
		if (!validationResult) registerError(result, FIRST_NAME_FIELD_NAME, EMPTY_FIELD_DESCRIPTION); 
		
		return validationResult;
	}
	
	private Boolean validateLastName(FirstStepRegistrationEntity data, FirstStepRegistrationResponseEntity result) {
		Boolean validationResult = !StringUtils.isEmpty(data.getLastName()); 
		
		if (!validationResult) registerError(result, LAST_NAME_FIELD_NAME, EMPTY_FIELD_DESCRIPTION);
		
		return validationResult;
	}
	
	private Boolean validateEmail(FirstStepRegistrationEntity data, FirstStepRegistrationResponseEntity result) {
		String email = data.getEmail();
				
		if (StringUtils.isEmpty(email)) {
			registerError(result, EMAIL_FIELD_NAME, EMPTY_FIELD_DESCRIPTION);
			return false;
		}
		
		if (!EmailValidator.getInstance().isValid(email)) {
			registerError(result, EMAIL_FIELD_NAME, INVALID_FORMAT_DESCRIPTION);
			return false;
		}
		
		if (proposalRepository.findByEmailIgnoreCase(email) != null) {
			registerError(result, EMAIL_FIELD_NAME, ALREADY_EXISTS_DESCRIPTION);
			return false;
		}

		return true;
	}
	
	private Boolean validateBirthDate(FirstStepRegistrationEntity data, FirstStepRegistrationResponseEntity result) {
		int requesterAge = getDiffYears(data.getBirthDate(), new java.util.Date());
		
		if (requesterAge < MAJORITY_AGE) {
			registerError(result, BIRTH_DATE_FIELD_NAME, INVALID_DATE_DESCRIPTION);
			return false;
		}
		
		return true;
	}
	
	private Boolean validateDriverLicenseNo(FirstStepRegistrationEntity data, FirstStepRegistrationResponseEntity result) {
		final int[][] driverLicenseValidationDigits = {{9, 8, 7, 6, 5, 4, 3, 2, 1},	{1, 2, 3, 4, 5, 6, 7, 8, 9}};
		
		String driverLicenseNo = data.getDriverLicenseNo();

		// 1. Check only the format. But this is necessary for the digit conference algorithm.
		if (!Pattern.matches(DRIVER_LICENSE_NO_REGEX, driverLicenseNo)) {
			registerError(result, DRIVER_LICENSE_NO_FIELD_NAME, INVALID_FORMAT_DESCRIPTION);
			return false;
		}		
		
		// 2. Now begins the conference by the digit.
		String licenseNo = driverLicenseNo.substring(0, 9);
		String licenseDigitNo = driverLicenseNo.substring(9);
		String conferenceDigit = "";
		
		int decrease = 0;
		for (int i = 1; i <= 2; i++) {
			int sum = 0;
			for (int j = 1; j <= 9; j++) {
				sum = sum + Integer.parseInt(licenseNo.substring(j-1, j)) * driverLicenseValidationDigits[i-1][j-1];
			}
			
			int digit = sum % 11;
			
			if (digit >= 10) {
				digit = 0;
				
				if (i == 1) {
					decrease = 2;
				}
			}
			
			if (i == 2) {
				digit = digit - decrease;
			}
			
			conferenceDigit = conferenceDigit + Integer.toString(digit).substring(0, 1);
		}
		
		if (!licenseDigitNo.equals(conferenceDigit)) {
			registerError(result, DRIVER_LICENSE_NO_FIELD_NAME, INVALID_FORMAT_DESCRIPTION);
			return false;
		}
		
		return true;
	}
	
	public FirstStepRegistrationResponseEntity validate(FirstStepRegistrationEntity data) {
		FirstStepRegistrationResponseEntity result = new FirstStepRegistrationResponseEntity();
		Boolean firstNameValidationResult = validateFirstName(data, result);
		Boolean secondNameValidationResult = validateLastName(data, result);
		Boolean emailValidationResult = validateEmail(data, result);
		Boolean birthDateValidationResult = validateBirthDate(data, result);
		Boolean driverLicenseNoValidationResult = validateDriverLicenseNo(data, result);
		
		result.setPassed(firstNameValidationResult && secondNameValidationResult && emailValidationResult
				&& birthDateValidationResult && driverLicenseNoValidationResult);
		return result;
	}
}
