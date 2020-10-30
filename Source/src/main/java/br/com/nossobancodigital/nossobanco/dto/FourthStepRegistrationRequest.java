package br.com.nossobancodigital.nossobanco.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FourthStepRegistrationRequest {
    private final String NOT_NULL = "null";

    @NotNull(message = NOT_NULL)
    private Boolean accepted;
}
