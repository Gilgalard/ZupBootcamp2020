package br.com.nossobancodigital.nossobanco.services;

import br.com.nossobancodigital.nossobanco.entities.SecondStepRegistrationRequest;
import br.com.nossobancodigital.nossobanco.entities.models.ProposalEntity;
import br.com.nossobancodigital.nossobanco.enums.ProposalStepEnum;
import br.com.nossobancodigital.nossobanco.repositories.ProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecondStepRegistrationService {
	private final ProposalRepository proposalRepository;

	public ProposalEntity save(
			Long id,
			SecondStepRegistrationRequest secondStepRegistrationRequest) {
		ProposalEntity proposalEntity = Optional.ofNullable(proposalRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)))
				.get();

		if (proposalEntity.getProposalStep() != ProposalStepEnum.FIRST_STEP) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
		}

		completeProposalEntity(proposalEntity, secondStepRegistrationRequest);
		proposalEntity.setProposalStep(ProposalStepEnum.SECOND_STEP);

		return proposalRepository.save(proposalEntity);
	}

	private void completeProposalEntity(
			ProposalEntity proposalEntity,
			SecondStepRegistrationRequest secondStepRegistrationRequest) {
		proposalEntity.setZipCode(secondStepRegistrationRequest.getZipCode());
		proposalEntity.setStreetName(secondStepRegistrationRequest.getStreetName());
		proposalEntity.setAddressComplement(secondStepRegistrationRequest.getAddressComplement());
		proposalEntity.setDistrictName(secondStepRegistrationRequest.getDistrictName());
		proposalEntity.setCityName(secondStepRegistrationRequest.getCityName());
		proposalEntity.setStateName(secondStepRegistrationRequest.getStateName());
	}
}