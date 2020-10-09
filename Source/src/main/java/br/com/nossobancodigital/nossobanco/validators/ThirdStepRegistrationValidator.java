package br.com.nossobancodigital.nossobanco.validators;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.nossobancodigital.nossobanco.entities.ThirdStepRegistrationEntity;
import br.com.nossobancodigital.nossobanco.entities.models.ProposalEntity;
import br.com.nossobancodigital.nossobanco.enums.ProposalStepEnum;
import br.com.nossobancodigital.nossobanco.repositories.ProposalRepository;
import br.com.nossobancodigital.nossobanco.responses.RegistrationErrorEntity;
import br.com.nossobancodigital.nossobanco.responses.RegistrationResponseEntity;
import br.com.nossobancodigital.nossobanco.responses.ThirdRegistrationResponseEntity;

@Component
public class ThirdStepRegistrationValidator {
	private final static String ID_FIELD_NAME = "id";
	private final static String FRONT_FIELD_NAME = "driverLicenseFrontImage";
	private final static String BACK_FIELD_NAME = "driverLicenseBackImage";
	private final static String EMPTY_FIELD_DESCRIPTION = "O campo não pode ser vazio.";
	private final static String PROPOSAL_NOT_FOUND_DESCRIPTION = "Id inválido.";
	
	@Autowired
	private ProposalRepository proposalRepository;
	
	private Boolean validateSecondStepRegistration(
			Long id,
			ThirdRegistrationResponseEntity result) {
		
		if (id == null) {
			result.getErrors().add(new RegistrationErrorEntity(ID_FIELD_NAME, PROPOSAL_NOT_FOUND_DESCRIPTION));
			
			return false;
		}
		
		Optional<ProposalEntity> proposalEntity = proposalRepository.findById(id);		
		if (proposalEntity.isPresent()) {
			result.setProposalExists(true);
			result.setPriorStepsCompleted(proposalEntity.get().getProposalStep() == ProposalStepEnum.SECOND_STEP);	
		}
		
		if (!result.getProposalExists() || !result.getPriorStepsCompleted()) {
			result.getErrors().add(new RegistrationErrorEntity(ID_FIELD_NAME, PROPOSAL_NOT_FOUND_DESCRIPTION));
			
			return false;
		}
		
		return true;
	}
	
	private Boolean validateFrontImage(String frontImage, RegistrationResponseEntity result) {
		if (StringUtils.isEmpty(frontImage)) {
		    result.getErrors().add(new RegistrationErrorEntity(FRONT_FIELD_NAME, EMPTY_FIELD_DESCRIPTION));
		    return false;
		}
		
		// Idea: we can pass the image on to a processing / AI service
		// Examples: The user sent an valid driver license image? Is it his name? Is it the same number
		//           he/she sent in the first step?

		return true;
	}
	
	private Boolean validateBackImage(String backImage, RegistrationResponseEntity result) {
		if (StringUtils.isEmpty(backImage)) {
			result.getErrors().add(new RegistrationErrorEntity(BACK_FIELD_NAME, EMPTY_FIELD_DESCRIPTION));
			return false;
		}

		return true;
	}
	
	public ThirdRegistrationResponseEntity validate(Long id, ThirdStepRegistrationEntity data) {
		ThirdRegistrationResponseEntity result = new ThirdRegistrationResponseEntity();
		Boolean SecondStepRegistrationValidationResult = validateSecondStepRegistration(id, result);
		Boolean frontImageValidationResult = validateFrontImage(data.getDriverLicenseFrontImage(), result);
		Boolean backImageValidationResult = validateBackImage(data.getDriverLicenseBackImage(), result);
		
		result.setPassed(SecondStepRegistrationValidationResult && frontImageValidationResult && backImageValidationResult);
		
		return result;
	}
}