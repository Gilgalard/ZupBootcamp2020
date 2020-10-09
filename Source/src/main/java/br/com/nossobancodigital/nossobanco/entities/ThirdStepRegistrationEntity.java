package br.com.nossobancodigital.nossobanco.entities;

public class ThirdStepRegistrationEntity {
	private String driverLicenseFrontImage;
	private String driverLicenseBackImage;
	
	public String getDriverLicenseFrontImage() {
		return driverLicenseFrontImage;
	}
	
	public void setDriverLicenseFrontImage(String driverLicenseFrontImage) {
		this.driverLicenseFrontImage = driverLicenseFrontImage;
	}
	
	public String getDriverLicenseBackImage() {
		return driverLicenseBackImage;
	}
	
	public void setDriverLicenseBackImage(String driverLicenseBackImage) {
		this.driverLicenseBackImage = driverLicenseBackImage;
	}
}
