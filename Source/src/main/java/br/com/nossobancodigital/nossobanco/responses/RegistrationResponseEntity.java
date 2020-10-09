package br.com.nossobancodigital.nossobanco.responses;

import java.util.ArrayList;
import java.util.List;

public class RegistrationResponseEntity {
	private Boolean passed = false;
	private Long id;
	private List<RegistrationErrorEntity> errors = new ArrayList<>();

	public Boolean getPassed() {
		return passed;
	}
	
	public void setPassed(Boolean passed) {
		this.passed = passed;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<RegistrationErrorEntity> getErrors() {
		return errors;
	}
	
	public void setErrors(List<RegistrationErrorEntity> errors) {
		this.errors = errors;
	}
}
