package br.com.nossobancodigital.nossobanco.services;

import br.com.nossobancodigital.nossobanco.entities.FourthStepRegistrationRequest;
import br.com.nossobancodigital.nossobanco.entities.models.ProposalEntity;
import br.com.nossobancodigital.nossobanco.enums.ProposalAcceptStatusEnum;
import br.com.nossobancodigital.nossobanco.enums.ProposalStepEnum;
import br.com.nossobancodigital.nossobanco.repositories.ProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FourthStepRegistrationService {
	private final String REFUSED_EMAIL_SUBJECT = "Refused email subject...";
	private final String REFUSED_EMAIL_BODY = "Refused email body.";
	private final String ACCEPTED_EMAIL_SUBJECT = "Accepted email subject...";
	private final String ACCEPTED_EMAIL_BODY = "Accepted email body.";

	private final ProposalRepository proposalRepository;
	private final JavaMailSender javaMailSender;
	
	public ProposalEntity get(Long id) {
		ProposalEntity proposalEntity = Optional.ofNullable(proposalRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)))
				.get();

		if (proposalEntity.getProposalStep() != ProposalStepEnum.THIRD_STEP) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return proposalEntity;
	}

	public void save(Long id, FourthStepRegistrationRequest fourthStepRegistrationRequest) {
		ProposalEntity proposalEntity = get(id);

		if (fourthStepRegistrationRequest.getAccepted()) {
			proposalEntity.setAcceptanceStatus(ProposalAcceptStatusEnum.ACCEPTED);
			proposalEntity.setProposalStep(ProposalStepEnum.FOURTH_STEP_ACCEPTED);
		} else {
			proposalEntity.setAcceptanceStatus(ProposalAcceptStatusEnum.REFUSED);
			proposalEntity.setProposalStep(ProposalStepEnum.FOURTH_STEP_REFUSED);
		}

		proposalRepository.save(proposalEntity);

		// Was not better put this service in a queue, and another service read it and process it?
		sendEmail(proposalEntity.getEmail(), fourthStepRegistrationRequest.getAccepted());
	}

	private void sendEmail(String toEmail, Boolean proposalAccepted) {
		EmailServiceImpl emailService = new EmailServiceImpl(javaMailSender);

		if (proposalAccepted) {
			emailService.sendSimpleMessage(toEmail, ACCEPTED_EMAIL_SUBJECT, ACCEPTED_EMAIL_BODY);
		} else {
			emailService.sendSimpleMessage(toEmail, REFUSED_EMAIL_SUBJECT, REFUSED_EMAIL_BODY);
		}
	}
}