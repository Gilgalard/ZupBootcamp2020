package br.com.nossobancodigital.nossobanco.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.nossobancodigital.nossobanco.entities.FirstStepRegistrationEntity;
import br.com.nossobancodigital.nossobanco.entities.SecondStepRegistrationEntity;
import br.com.nossobancodigital.nossobanco.entities.ThirdStepRegistrationEntity;
import br.com.nossobancodigital.nossobanco.responses.RegistrationResponseEntity;
import br.com.nossobancodigital.nossobanco.responses.ThirdRegistrationResponseEntity;
import br.com.nossobancodigital.nossobanco.services.FirstStepRegistrationService;
import br.com.nossobancodigital.nossobanco.services.SecondStepRegistrationService;
import br.com.nossobancodigital.nossobanco.services.ThirdStepRegistrationService;

@Controller
public class NossoBancoDigitalController {
	@Autowired
	FirstStepRegistrationService firstStepRegistrationService;

	@Autowired
	SecondStepRegistrationService secondStepRegistrationService;

	@Autowired
	ThirdStepRegistrationService thirdStepRegistrationService;
	
	private enum RegistrationStep {
		FIRST("secondStepRegistration"),
		SECOND("thirdStepRegistration"),
		THIRD("fourthStepRegistration");
		
		private String nextEndpoint;
		
		RegistrationStep(String nextEndpoint) {
			this.nextEndpoint = nextEndpoint;
		}
		
		public String getNextEndpoint() {
			return nextEndpoint;
		}
	}
	
	public ResponseEntity<?> defaultRegistrationResponse(
			HttpStatus httpStatus,
			RegistrationResponseEntity response,
			RegistrationStep step) {
		final HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> responseEntity = null;
		
		if (httpStatus.equals(HttpStatus.CREATED)) {
			final URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
					.path("/" + step.getNextEndpoint() + "/{id}").build()
					.expand(response.getId()).toUri();
			headers.setLocation(location);
			responseEntity = new ResponseEntity<Void>(headers, httpStatus);
		} else {
			responseEntity = ResponseEntity.status(httpStatus).body(response.getErrors());
		}		
		
		return responseEntity;
	}
	
	public ResponseEntity<?> firstAndSecondRegistrationResponse(
			RegistrationResponseEntity response,
			RegistrationStep step) {
		HttpStatus httpStatus = null;
		
		if (Boolean.TRUE.equals(response.getPassed())) {
			httpStatus = HttpStatus.CREATED;
		} else {
			httpStatus = HttpStatus.BAD_REQUEST;
		}		
		
		return defaultRegistrationResponse(httpStatus, response, step);
	}
	
	@PostMapping("/firstStepRegistration")
	public ResponseEntity<?> postFirstStepRegistration(@RequestBody FirstStepRegistrationEntity firstStepRegistrationEntity) {
		final RegistrationResponseEntity result = firstStepRegistrationService.save(firstStepRegistrationEntity);
		
		return firstAndSecondRegistrationResponse(result, RegistrationStep.FIRST);
	}
	
	@PostMapping("/secondStepRegistration/{id}")
	public ResponseEntity<?> postSecondStepRegistration(
			@PathVariable Long id, 
			@RequestBody SecondStepRegistrationEntity secondStepRegistrationEntity) {
		final RegistrationResponseEntity result = secondStepRegistrationService.save(id, secondStepRegistrationEntity);
		
		return firstAndSecondRegistrationResponse(result, RegistrationStep.SECOND);
	}
	
	@PostMapping("/thirdStepRegistration/{id}")
	public ResponseEntity<?> postThirdStepRegistration(
			@PathVariable Long id, 
			@RequestBody ThirdStepRegistrationEntity thirdStepRegistrationEntity) {
		final ThirdRegistrationResponseEntity result = thirdStepRegistrationService.save(id, thirdStepRegistrationEntity);
		HttpStatus httpStatus = null;
		
		if (Boolean.TRUE.equals(result.getPassed())) {
			httpStatus = HttpStatus.CREATED;
		} else { // Validation failed. In this step, we need to return de specific code.
			if (result.getProposalExists() && result.getPriorStepsCompleted()) {
				httpStatus = HttpStatus.BAD_REQUEST;
			} else if (!result.getProposalExists()) {
				httpStatus = HttpStatus.NOT_FOUND;
			} else {
				httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			}
		}
		
		return defaultRegistrationResponse(httpStatus, result, RegistrationStep.THIRD);
	}
}
