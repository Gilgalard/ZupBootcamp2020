package br.com.nossobancodigital.nossobanco.entities.converters;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.nossobancodigital.nossobanco.enums.ProposalStepEnum;

@Converter(autoApply=true)
public class ProposalStepConverter implements AttributeConverter<ProposalStepEnum, Integer> {
	@Override
    public Integer convertToDatabaseColumn (ProposalStepEnum proposalStepEnum) {
		if (proposalStepEnum == null) {
			return null;
		}
		
		return proposalStepEnum.getStepValue();
	}

	@Override
    public ProposalStepEnum convertToEntityAttribute (Integer stepValue) {
    	if (stepValue == null) {
    		return null;
    	}
    	
    	return Stream.of(ProposalStepEnum.values())
    			.filter(step -> stepValue.equals(step.getStepValue()))
    			.findFirst()
    			.orElse(null);
    }
}