package br.com.nossobancodigital.nossobanco.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nossobancodigital.nossobanco.entities.ThirdStepRegistrationEntity;
import br.com.nossobancodigital.nossobanco.entities.models.ProposalEntity;
import br.com.nossobancodigital.nossobanco.enums.ProposalStepEnum;
import br.com.nossobancodigital.nossobanco.repositories.ProposalRepository;
import br.com.nossobancodigital.nossobanco.responses.ThirdRegistrationResponseEntity;
import br.com.nossobancodigital.nossobanco.validators.ThirdStepRegistrationValidator;

@Service
public class ThirdStepRegistrationService {
	@Autowired
	private ProposalRepository proposalRepository;
	
	@Autowired
	ThirdStepRegistrationValidator validator;
	
	private ProposalEntity convertToProposal(Long id, ThirdStepRegistrationEntity thirdStepRegistrationEntity) {
		ProposalEntity proposalEntity = proposalRepository.findById(id).get(); // It's safe to do that because this
		                                                                       // method is only called when passed
		                                                                       // to validations, which check for the
		                                                                       // non-nullify of the id and the existence
		                                                                       // of the proposal.
		proposalEntity.setDriverLicenseFrontImage(thirdStepRegistrationEntity.getDriverLicenseFrontImage());
		proposalEntity.setDriverLicenseBackImage(thirdStepRegistrationEntity.getDriverLicenseBackImage());
		
		return proposalEntity;
	}
	
	public ThirdRegistrationResponseEntity save(Long id, ThirdStepRegistrationEntity thirdStepRegistrationEntity) {
		ThirdRegistrationResponseEntity response = validator.validate(id, thirdStepRegistrationEntity);
		
		if (response.getPassed()) {
			ProposalEntity proposal = convertToProposal(id, thirdStepRegistrationEntity);
			proposal.setProposalStep(ProposalStepEnum.THIRD_STEP);			
			proposalRepository.save(proposal);
			
			response.setId(id);
		}
		
		return response;
	}
}
