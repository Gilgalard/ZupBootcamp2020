package br.com.nossobancodigital.nossobanco.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nossobancodigital.nossobanco.entities.FirstStepRegistrationEntity;
import br.com.nossobancodigital.nossobanco.entities.models.ProposalEntity;
import br.com.nossobancodigital.nossobanco.enums.ProposalAcceptStatusEnum;
import br.com.nossobancodigital.nossobanco.enums.ProposalStepEnum;
import br.com.nossobancodigital.nossobanco.repositories.ProposalRepository;
import br.com.nossobancodigital.nossobanco.responses.FirstStepRegistrationResponseEntity;
import br.com.nossobancodigital.nossobanco.validators.FirstStepRegistrationValidator;

@Service
public class FirstStepRegistrationService {	
	@Autowired
	private ProposalRepository proposalRepository;
	
	@Autowired
	FirstStepRegistrationValidator validator;
	
	private ProposalEntity convertToProposal(FirstStepRegistrationEntity firstStepRegistrationEntity) {
		ProposalEntity proposal = new ProposalEntity();
		proposal.setFirstName(firstStepRegistrationEntity.getFirstName());
		proposal.setLastName(firstStepRegistrationEntity.getLastName());
		proposal.setEmail(firstStepRegistrationEntity.getEmail());
		proposal.setDriverLicenseNo(firstStepRegistrationEntity.getDriverLicenseNo());
		proposal.setBirthDate(firstStepRegistrationEntity.getBirthDate());
		
		return proposal;
	}
	
	public FirstStepRegistrationResponseEntity save(FirstStepRegistrationEntity firstStepRegistrationEntity) {
		FirstStepRegistrationResponseEntity response = validator.validate(firstStepRegistrationEntity);
		
		if (response.getPassed()) {
			ProposalEntity proposal = convertToProposal(firstStepRegistrationEntity);
			proposal.setProposalStep(ProposalStepEnum.FIRST_STEP);
			proposal.setAcceptanceStatus(ProposalAcceptStatusEnum.PENDING);
			
			Long id = proposalRepository.save(proposal).getId();
			
			response.setId(id);
		}
		
		return response;
	}
}
