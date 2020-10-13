package br.com.nossobancodigital.nossobanco.enums;

public enum RegistrationStepEnum {
    FIRST("secondStepRegistration"),
    SECOND("thirdStepRegistration"),
    THIRD("fourthStepRegistration");

    private String nextEndpoint;

    RegistrationStepEnum(String nextEndpoint) {
        this.nextEndpoint = nextEndpoint;
    }

    public String getNextEndpoint() {
        return nextEndpoint;
    }
}