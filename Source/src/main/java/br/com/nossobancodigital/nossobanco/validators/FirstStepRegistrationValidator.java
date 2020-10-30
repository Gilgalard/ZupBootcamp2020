package br.com.nossobancodigital.nossobanco.validators;

import br.com.nossobancodigital.nossobanco.dto.FirstStepRegistrationRequest;
import br.com.nossobancodigital.nossobanco.repositories.ProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class FirstStepRegistrationValidator implements Validator {
	private final String EMAIL_FIELD = "email";
	private final String EMAIL_DUPLICATED = "duplicated";

	private final ProposalRepository proposalRepository;

	@Override
	public boolean supports(Class<?> paramClass) {
		return FirstStepRegistrationRequest.class.isAssignableFrom(paramClass);
	}

	@Override
	public void validate(Object object, Errors errors) {
		FirstStepRegistrationRequest firstStepRegistrationRequest = (FirstStepRegistrationRequest)object;
		if (!validateEmail(firstStepRegistrationRequest.getEmail())) {
			errors.rejectValue(EMAIL_FIELD, EMAIL_DUPLICATED, new Object[]{EMAIL_FIELD}, EMAIL_DUPLICATED);
		}
	}

	private Boolean validateEmail(String email) {
		return (proposalRepository.findByEmailIgnoreCase(email) == null);
	}
}