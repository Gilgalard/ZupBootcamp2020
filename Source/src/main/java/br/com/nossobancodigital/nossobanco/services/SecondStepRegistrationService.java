package br.com.nossobancodigital.nossobanco.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nossobancodigital.nossobanco.entities.SecondStepRegistrationEntity;
import br.com.nossobancodigital.nossobanco.entities.models.ProposalEntity;
import br.com.nossobancodigital.nossobanco.enums.ProposalStepEnum;
import br.com.nossobancodigital.nossobanco.repositories.ProposalRepository;
import br.com.nossobancodigital.nossobanco.responses.RegistrationResponseEntity;
import br.com.nossobancodigital.nossobanco.validators.SecondStepRegistrationValidator;

@Service
public class SecondStepRegistrationService {
	@Autowired
	private ProposalRepository proposalRepository;
	
	@Autowired
	SecondStepRegistrationValidator validator;
	
	private ProposalEntity convertToProposal(Long id, SecondStepRegistrationEntity secondStepRegistrationEntity) {
		ProposalEntity proposalEntity = proposalRepository.findById(id).get(); // It's safe to do that because this
		                                                                       // method is only called when passed
		                                                                       // to validations, which check for the
		                                                                       // non-nullify of the id and the existence
		                                                                       // of the proposal.

		proposalEntity.setZipCode(secondStepRegistrationEntity.getZipCode());
		proposalEntity.setStreetName(secondStepRegistrationEntity.getStreetName());
		proposalEntity.setAddressComplement(secondStepRegistrationEntity.getAddressComplement());
		proposalEntity.setDistrictName(secondStepRegistrationEntity.getDistrictName());
		proposalEntity.setCityName(secondStepRegistrationEntity.getCityName());
		proposalEntity.setStateName(secondStepRegistrationEntity.getStateName());
		
		return proposalEntity;
	}
	
	public RegistrationResponseEntity save(Long id, SecondStepRegistrationEntity secondStepRegistrationEntity) {
		RegistrationResponseEntity response = validator.validate(id, secondStepRegistrationEntity);
		
		if (response.getPassed()) {
			ProposalEntity proposal = convertToProposal(id, secondStepRegistrationEntity);
			proposal.setProposalStep(ProposalStepEnum.SECOND_STEP);
			
			proposalRepository.save(proposal);
			
			response.setId(id);
		}
		
		return response;
	}
}