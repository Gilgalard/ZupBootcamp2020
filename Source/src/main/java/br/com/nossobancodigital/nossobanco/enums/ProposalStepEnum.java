package br.com.nossobancodigital.nossobanco.enums;

public enum ProposalStepEnum {
	FIRST_STEP(100),
	SECOND_STEP(200),
	THIRD_STEP(300),
	FOURTH_STEP_ACCEPTED(410),
	FOURTH_STEP_REFUSED(450);
	
	private int stepValue;
	
	ProposalStepEnum(int stepValue) {
		this.stepValue = stepValue;
	}
	
	public int getStepValue() {
		return stepValue;
	}
}
