package br.com.nossobancodigital.nossobanco.validators;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.nossobancodigital.nossobanco.entities.SecondStepRegistrationEntity;
import br.com.nossobancodigital.nossobanco.entities.models.ProposalEntity;
import br.com.nossobancodigital.nossobanco.enums.ProposalStepEnum;
import br.com.nossobancodigital.nossobanco.repositories.ProposalRepository;
import br.com.nossobancodigital.nossobanco.responses.RegistrationErrorEntity;
import br.com.nossobancodigital.nossobanco.responses.RegistrationResponseEntity;

@Component
public class SecondStepRegistrationValidator {
	private static final String ZIP_CODE_REGEX = "[0-9]{7,8}";

	// Validation errors constants.
	private final static String ID_FIELD_NAME = "id";
	private final static String ZIP_CODE_FIELD_NAME = "zipCode";
	private final static String STREET_NAME_FIELD_NAME = "streetName";
	private final static String ADDRESS_COMPLEMENT_FIELD_NAME = "addressComplement";
	private final static String DISTRICT_NAME_FIELD_NAME = "districtName";
	private final static String CITY_NAME_FIELD_NAME = "cityName";
	private final static String STATE_NAME_FIELD_NAME = "stateName";
	private final static String EMPTY_FIELD_DESCRIPTION = "O campo não pode ser vazio.";
	private final static String INVALID_FORMAT_DESCRIPTION = "Formato inválido.";
	private final static String PROPOSAL_NOT_FOUND_DESCRIPTION = "Id inválido.";
	
	@Autowired
	private ProposalRepository proposalRepository;
	
	private Boolean validateFirstStepRegistration(
			Long id,
			RegistrationResponseEntity result) {
		
		if (id == null) {
			result.getErrors().add(new RegistrationErrorEntity(ID_FIELD_NAME, PROPOSAL_NOT_FOUND_DESCRIPTION));
			
			return false;
		}
		
		Optional<ProposalEntity> proposalEntity = proposalRepository.findById(id);		
		if (proposalEntity.isEmpty() || proposalEntity.get().getProposalStep() != ProposalStepEnum.FIRST_STEP) {
			result.getErrors().add(new RegistrationErrorEntity(ID_FIELD_NAME, PROPOSAL_NOT_FOUND_DESCRIPTION));
			
			return false;
		}
		
		return true;
	}
	
	private Boolean validateZipCode(Integer zipCode, RegistrationResponseEntity result) {
		if (zipCode == null) {
		    result.getErrors().add(new RegistrationErrorEntity(ZIP_CODE_FIELD_NAME, EMPTY_FIELD_DESCRIPTION));
		    return false;
		}
		
		if (!Pattern.matches(ZIP_CODE_REGEX, zipCode.toString())) {
			result.getErrors().add(new RegistrationErrorEntity(ZIP_CODE_FIELD_NAME, INVALID_FORMAT_DESCRIPTION));
			return false;
		}		

		return true;
	}
	
	private Boolean validateStreetName(String streetName, RegistrationResponseEntity result) {
		if (StringUtils.isEmpty(streetName)) {
			result.getErrors().add(new RegistrationErrorEntity(STREET_NAME_FIELD_NAME, EMPTY_FIELD_DESCRIPTION));
			return false;
		}

		return true;
	}
	
	private Boolean validateAddressComplement(String addressComplement, RegistrationResponseEntity result) {
		if (StringUtils.isEmpty(addressComplement)) {
			result.getErrors().add(new RegistrationErrorEntity(ADDRESS_COMPLEMENT_FIELD_NAME, EMPTY_FIELD_DESCRIPTION));
			return false;
		}
		
		return true;
	}
	
	private Boolean validateDistrictName(String districtName, RegistrationResponseEntity result) {
		if (StringUtils.isEmpty(districtName)) {
			result.getErrors().add(new RegistrationErrorEntity(DISTRICT_NAME_FIELD_NAME, EMPTY_FIELD_DESCRIPTION));
			return false;
		}
		
		return true;
	}
	
	private Boolean validateCityName(String cityName, RegistrationResponseEntity result) {
		if (StringUtils.isEmpty(cityName)) {
			result.getErrors().add(new RegistrationErrorEntity(CITY_NAME_FIELD_NAME, EMPTY_FIELD_DESCRIPTION));
			return false;
		}
		
		return true;
	}
	
	private Boolean validateStateName(String stateName, RegistrationResponseEntity result) {
		if (StringUtils.isEmpty(stateName)) {
			result.getErrors().add(new RegistrationErrorEntity(STATE_NAME_FIELD_NAME, EMPTY_FIELD_DESCRIPTION));
			return false;
		}
		
		return true;
	}
	
	public RegistrationResponseEntity validate(Long id, SecondStepRegistrationEntity data) {
		RegistrationResponseEntity result = new RegistrationResponseEntity();
		Boolean firstStepRegistrationValidationResult = validateFirstStepRegistration(id, result);
		Boolean zipCodeValidationResult = validateZipCode(data.getZipCode(), result);
		Boolean streetNameValidationResult = validateStreetName(data.getStreetName(), result);
		Boolean addressComplementValidationResult = validateAddressComplement(data.getAddressComplement(), result);
		Boolean districtNameValidationResult = validateDistrictName(data.getDistrictName(), result);
		Boolean cityNameValidationResult = validateCityName(data.getCityName(), result);
		Boolean stateNameValidationResult = validateStateName(data.getStateName(), result);
		
		result.setPassed(firstStepRegistrationValidationResult && zipCodeValidationResult
				&& streetNameValidationResult && addressComplementValidationResult 
				&& districtNameValidationResult && cityNameValidationResult
				&& stateNameValidationResult);
		
		return result;
	}
}
