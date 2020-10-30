package br.com.nossobancodigital.nossobanco.services;

import br.com.nossobancodigital.nossobanco.dto.ThirdStepRegistrationRequest;
import br.com.nossobancodigital.nossobanco.entities.ProposalEntity;
import br.com.nossobancodigital.nossobanco.enums.ProposalStepEnum;
import br.com.nossobancodigital.nossobanco.repositories.ProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThirdStepRegistrationService {
	private final ProposalRepository proposalRepository;

	public ProposalEntity save(Long id, ThirdStepRegistrationRequest thirdStepRegistrationRequest) {
		ProposalEntity proposalEntity = Optional.ofNullable(proposalRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)))
				.get();

		if (proposalEntity.getProposalStep() != ProposalStepEnum.SECOND_STEP) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
		}

		completeProposalEntity(proposalEntity, thirdStepRegistrationRequest);
		proposalEntity.setProposalStep(ProposalStepEnum.THIRD_STEP);

		return proposalRepository.save(proposalEntity);
	}

	private void completeProposalEntity(
			ProposalEntity proposalEntity,
			ThirdStepRegistrationRequest thirdStepRegistrationRequest) {
		proposalEntity.setDriverLicenseFrontImage(thirdStepRegistrationRequest.getDriverLicenseFrontImage());
		proposalEntity.setDriverLicenseBackImage(thirdStepRegistrationRequest.getDriverLicenseBackImage());
	}
}