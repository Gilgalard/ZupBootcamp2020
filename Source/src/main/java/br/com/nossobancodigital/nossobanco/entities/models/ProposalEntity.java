package br.com.nossobancodigital.nossobanco.entities.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import br.com.nossobancodigital.nossobanco.enums.ProposalAcceptStatusEnum;
import br.com.nossobancodigital.nossobanco.enums.ProposalStepEnum;

@Entity
@Table(name="proposal")
public class ProposalEntity {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	private String firstName;
	
	@Column(nullable=false)
	private String lastName;
	
	@Column(nullable=false, unique=true)
	private String email;
	
	@Column(length=20)
	private String driverLicenseNo;
	
	private Date birthDate;
	
	private int zipCode;
	private String streetName;
	private String streetComplement;
	private String districtName;
	private String cityName;
	private String stateName;
	
	@Lob
	private String driverLicenseFrontImage;
	
	@Lob
	private String driverLicenseBackImage;
	
	private ProposalStepEnum proposalStep;
	
	@Enumerated(EnumType.ORDINAL)
	private ProposalAcceptStatusEnum acceptanceStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDriverLicenseNo() {
		return driverLicenseNo;
	}

	public void setDriverLicenseNo(String driverLicenseNo) {
		this.driverLicenseNo = driverLicenseNo;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getStreetComplement() {
		return streetComplement;
	}

	public void setStreetComplement(String streetComplement) {
		this.streetComplement = streetComplement;
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

	public ProposalStepEnum getProposalStep() {
		return proposalStep;
	}

	public void setProposalStep(ProposalStepEnum proposalStep) {
		this.proposalStep = proposalStep;
	}

	public ProposalAcceptStatusEnum getAcceptanceStatus() {
		return acceptanceStatus;
	}

	public void setAcceptanceStatus(ProposalAcceptStatusEnum acceptanceStatus) {
		this.acceptanceStatus = acceptanceStatus;
	}
}
