package br.com.nossobancodigital.nossobanco.services;

import br.com.nossobancodigital.nossobanco.dto.FirstStepRegistrationRequest;
import br.com.nossobancodigital.nossobanco.entities.ProposalEntity;
import br.com.nossobancodigital.nossobanco.enums.ProposalAcceptStatusEnum;
import br.com.nossobancodigital.nossobanco.enums.ProposalStepEnum;
import br.com.nossobancodigital.nossobanco.repositories.ProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirstStepRegistrationService {	
	private final ProposalRepository proposalRepository;
	
	private ProposalEntity createProposalEntityFromFirstStepRegistrationRequest(
			FirstStepRegistrationRequest firstStepRegistrationRequest) {
		ProposalEntity proposal = new ProposalEntity();
		proposal.setFirstName(firstStepRegistrationRequest.getFirstName());
		proposal.setLastName(firstStepRegistrationRequest.getLastName());
		proposal.setEmail(firstStepRegistrationRequest.getEmail());
		proposal.setDriverLicenseNo(firstStepRegistrationRequest.getDriverLicenseNo());
		proposal.setBirthDate(firstStepRegistrationRequest.getBirthDate());
		
		return proposal;
	}
	
	public ProposalEntity save(FirstStepRegistrationRequest firstStepRegistrationRequest) {
		ProposalEntity proposalEntity =
				createProposalEntityFromFirstStepRegistrationRequest(firstStepRegistrationRequest);
		proposalEntity.setProposalStep(ProposalStepEnum.FIRST_STEP);
		proposalEntity.setAcceptanceStatus(ProposalAcceptStatusEnum.PENDING);

		return proposalRepository.save(proposalEntity);
	}
}
