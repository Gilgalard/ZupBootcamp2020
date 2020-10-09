package br.com.nossobancodigital.nossobanco.entities;

public class SecondStepRegistrationEntity {
	private Integer zipCode;
	private String streetName;
	private String addressComplement;
	private String districtName;
	private String cityName;
	private String stateName;
	
	public Integer getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getStreetName() {
		return streetName;
	}
	
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	
	public String getAddressComplement() {
		return addressComplement;
	}
	
	public void setAddressComplement(String addressComplement) {
		this.addressComplement = addressComplement;
	}
	
	public String getDistrictName() {
		return districtName;
	}
	
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getStateName() {
		return stateName;
	}
	
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
