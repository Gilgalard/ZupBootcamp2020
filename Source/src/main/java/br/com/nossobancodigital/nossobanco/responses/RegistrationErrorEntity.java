package br.com.nossobancodigital.nossobanco.responses;

public class RegistrationErrorEntity {
	private String fieldName;
	private String description;
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
