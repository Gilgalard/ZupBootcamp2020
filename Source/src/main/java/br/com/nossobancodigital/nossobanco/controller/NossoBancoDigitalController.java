package br.com.nossobancodigital.nossobanco.controller;

import br.com.nossobancodigital.nossobanco.dto.FirstStepRegistrationRequest;
import br.com.nossobancodigital.nossobanco.dto.FourthStepRegistrationRequest;
import br.com.nossobancodigital.nossobanco.dto.SecondStepRegistrationRequest;
import br.com.nossobancodigital.nossobanco.dto.ThirdStepRegistrationRequest;
import br.com.nossobancodigital.nossobanco.entities.ProposalEntity;
import br.com.nossobancodigital.nossobanco.services.FirstStepRegistrationService;
import br.com.nossobancodigital.nossobanco.services.FourthStepRegistrationService;
import br.com.nossobancodigital.nossobanco.services.SecondStepRegistrationService;
import br.com.nossobancodigital.nossobanco.services.ThirdStepRegistrationService;
import br.com.nossobancodigital.nossobanco.validators.FirstStepRegistrationValidator;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/v1", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NossoBancoDigitalController {
    private final FirstStepRegistrationService firstStepRegistrationService;
    private final SecondStepRegistrationService secondStepRegistrationService;
    private final ThirdStepRegistrationService thirdStepRegistrationService;
    private final FourthStepRegistrationService fourthStepRegistrationService;

    private final FirstStepRegistrationValidator firstStepRegistrationValidator;

    @InitBinder("firstStepRegistrationRequest")
    private void initBinderFirstStepRegistrationRequest(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(firstStepRegistrationValidator);
    }

    @PostMapping("/firstStepRegistration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> postFirstStepRegistration(
            @Valid @RequestBody FirstStepRegistrationRequest firstStepRegistrationRequest) {
        final ProposalEntity proposalEntity = firstStepRegistrationService.save(firstStepRegistrationRequest);

        return ResponseEntity.created(URI.create("/secondStepRegistration/" + proposalEntity.getId())).build();
    }

    @PostMapping("/secondStepRegistration/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> postSecondStepRegistration(
            @PathVariable Long id,
            @Valid @RequestBody SecondStepRegistrationRequest secondStepRegistrationRequest) {
        secondStepRegistrationService.save(id, secondStepRegistrationRequest);

        return ResponseEntity.created(URI.create("/thirdStepRegistration/" + id)).build();
    }

    @PostMapping("/thirdStepRegistration/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> postThirdStepRegistration(
            @PathVariable Long id,
            @Valid @RequestBody ThirdStepRegistrationRequest thirdStepRegistrationRequest) {
        thirdStepRegistrationService.save(id, thirdStepRegistrationRequest);

        return ResponseEntity.created(URI.create("/fourthStepRegistration/" + id)).build();
    }

    @GetMapping("/fourthStepRegistration/{id}")
    public ResponseEntity<Object> getFourthStepRegistration(@PathVariable Long id) {
        return ResponseEntity.ok(fourthStepRegistrationService.get(id));
    }

    @PostMapping("/fourthStepRegistration/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> postFourthStepRegistration(
            @PathVariable Long id,
            @Valid @RequestBody FourthStepRegistrationRequest fourthStepRegistrationRequest) {
        fourthStepRegistrationService.save(id, fourthStepRegistrationRequest);

        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = new HashMap<>();

        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<Object> handleEntityNotFoundExceptions(EntityNotFoundException entityNotFoundException) {
        return ResponseEntity.notFound().build();
    }
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnsupportedOperationException.class)
    ResponseEntity<Object> handleUnsupportedOperationException(UnsupportedOperationException UnsupportedOperationException) {
        return ResponseEntity.notFound().build();
    }
}