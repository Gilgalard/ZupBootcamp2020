package br.com.nossobancodigital.nossobanco.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.nossobancodigital.nossobanco.entities.FirstStepRegistrationEntity;
import br.com.nossobancodigital.nossobanco.responses.FirstStepRegistrationResponseEntity;
import br.com.nossobancodigital.nossobanco.services.FirstStepRegistrationService;

@Controller
public class NossoBancoDigitalController {
	@Autowired
	FirstStepRegistrationService firstStepRegistrationService;

	@Autowired
	SecondStepRegistrationService secondStepRegistrationService;
	
	@PostMapping("/firstStepRegistration")
	public ResponseEntity<?> postFirstStepRegistration(@RequestBody FirstStepRegistrationEntity firstStepRegistrationEntity) {
		final FirstStepRegistrationResponseEntity result = firstStepRegistrationService.save(firstStepRegistrationEntity);
		final HttpHeaders headers = new HttpHeaders();
		HttpStatus httpStatus = null;
		ResponseEntity<?> responseEntity = null;
		
		if(Boolean.TRUE.equals(result.getPassed())) {
			final URI location = ServletUriComponentsBuilder
					.fromCurrentServletMapping().path("/secondStepRegistration/{id}")
					.build().expand(result.getId()).toUri();
			headers.setLocation(location);
			httpStatus = HttpStatus.CREATED;
			
			responseEntity = new ResponseEntity<Void>(headers, httpStatus);
		} else {
			httpStatus = HttpStatus.BAD_REQUEST;
			
			responseEntity = ResponseEntity.status(httpStatus).body(result.getErrors());
		}		
		
		return responseEntity;
	}
}
