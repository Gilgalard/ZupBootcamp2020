package br.com.nossobancodigital.nossobanco.controllers;

import br.com.nossobancodigital.nossobanco.controller.NossoBancoDigitalController;
import br.com.nossobancodigital.nossobanco.entities.FirstStepRegistrationRequest;
import br.com.nossobancodigital.nossobanco.entities.models.ProposalEntity;
import br.com.nossobancodigital.nossobanco.repositories.ProposalRepository;
import br.com.nossobancodigital.nossobanco.services.FirstStepRegistrationService;
import br.com.nossobancodigital.nossobanco.services.FourthStepRegistrationService;
import br.com.nossobancodigital.nossobanco.services.SecondStepRegistrationService;
import br.com.nossobancodigital.nossobanco.services.ThirdStepRegistrationService;
import br.com.nossobancodigital.nossobanco.validators.FirstStepRegistrationValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(NossoBancoDigitalController.class)
@RequiredArgsConstructor
public class NossoBancoDigitalControllerTest {
    private static final Long ID = 1L;
    private static final String FIRST_NAME = "First name";
    private static final String LAST_NAME = "Last name";
    private static final String EMAIL = "email@company.com";
    private static final String DRIVER_LICENSE_NO = "00000000000";
    private static final Date BIRTH_DATE = new Date(2000, 0, 1);

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private FirstStepRegistrationService firstStepRegistrationService;
    @MockBean private SecondStepRegistrationService secondStepRegistrationService;
    @MockBean private ThirdStepRegistrationService thirdStepRegistrationService;
    @MockBean private FourthStepRegistrationService fourthStepRegistrationService;
    @Spy private ProposalRepository proposalRepository;
    @SpyBean private FirstStepRegistrationValidator firstStepRegistrationValidator;

    @Test
    @Description("Oi")
    public void postFirstStepRegistrationSucceed() throws Exception {
        FirstStepRegistrationRequest firstStepRegistrationRequest = createFirstStepRegistrationRequest(true);
        String firstStepRegistrationRequestString = objectMapper.writeValueAsString(firstStepRegistrationRequest);

        ProposalEntity proposalEntity = new ProposalEntity();
        proposalEntity.setId(1L);

        when(firstStepRegistrationService.save(any(FirstStepRegistrationRequest.class))).thenReturn(proposalEntity);

        mockMvc.perform(post("/firstStepRegistration").contentType(MediaType.APPLICATION_JSON).content(firstStepRegistrationRequestString)).andExpect(status().isOk());
    }

    private FirstStepRegistrationRequest createFirstStepRegistrationRequest(Boolean withData) {
        FirstStepRegistrationRequest firstStepRegistrationRequest = new FirstStepRegistrationRequest();

        if (withData) {
            firstStepRegistrationRequest.setFirstName(FIRST_NAME);
            firstStepRegistrationRequest.setLastName(LAST_NAME);
            firstStepRegistrationRequest.setEmail(EMAIL);
            firstStepRegistrationRequest.setDriverLicenseNo(DRIVER_LICENSE_NO);
            firstStepRegistrationRequest.setBirthDate(BIRTH_DATE);
        }

        return firstStepRegistrationRequest;
    }
}
