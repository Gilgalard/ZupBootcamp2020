package br.com.nossobancodigital.nossobanco.responses;

public class ThirdRegistrationResponseEntity extends RegistrationResponseEntity {
	private Boolean proposalExists = false;
	private Boolean priorStepsCompleted = false;
	
	public Boolean getProposalExists() {
		return proposalExists;
	}
	
	public void setProposalExists(Boolean proposalExists) {
		this.proposalExists = proposalExists;
	}
	
	public Boolean getPriorStepsCompleted() {
		return priorStepsCompleted;
	}
	
	public void setPriorStepsCompleted(Boolean priorStepsCompleted) {
		this.priorStepsCompleted = priorStepsCompleted;
	}
}
