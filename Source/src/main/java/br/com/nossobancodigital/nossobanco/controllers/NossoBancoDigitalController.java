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
import br.com.nossobancodigital.nossobanco.responses.RegistrationResponseEntity;
import br.com.nossobancodigital.nossobanco.services.FirstStepRegistrationService;
import br.com.nossobancodigital.nossobanco.services.SecondStepRegistrationService;

@Controller
public class NossoBancoDigitalController {
	@Autowired
	FirstStepRegistrationService firstStepRegistrationService;

	@Autowired
	SecondStepRegistrationService secondStepRegistrationService;
	
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
			RegistrationResponseEntity response,
			RegistrationStep step) {
		final HttpHeaders headers = new HttpHeaders();
		HttpStatus httpStatus = null;
		ResponseEntity<?> responseEntity = null;
		
		if(Boolean.TRUE.equals(response.getPassed())) {
			final URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
					.path("/" + step.getNextEndpoint() + "/{id}").build()
					.expand(response.getId()).toUri();
			headers.setLocation(location);
			responseEntity = new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		} else {
			httpStatus = HttpStatus.BAD_REQUEST;
			
			responseEntity = ResponseEntity.status(httpStatus).body(response.getErrors());
		}		
		
		return responseEntity;
	}
	
	@PostMapping("/firstStepRegistration")
	public ResponseEntity<?> postFirstStepRegistration(@RequestBody FirstStepRegistrationEntity firstStepRegistrationEntity) {
		final RegistrationResponseEntity result = firstStepRegistrationService.save(firstStepRegistrationEntity);
		
		return defaultRegistrationResponse(result, RegistrationStep.FIRST);
	}
	
	@PostMapping("/secondStepRegistration/{id}")
	public ResponseEntity<?> postSecondStepRegistration(
			@PathVariable Long id, 
			@RequestBody SecondStepRegistrationEntity secondStepRegistrationEntity) {
		final RegistrationResponseEntity result = secondStepRegistrationService.save(id, secondStepRegistrationEntity);
		
		return defaultRegistrationResponse(result, RegistrationStep.SECOND);
	}
}
