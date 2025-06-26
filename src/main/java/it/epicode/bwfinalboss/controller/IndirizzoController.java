package it.epicode.bwfinalboss.controller;



import it.epicode.bwfinalboss.dto.IndirizzoDto;
import it.epicode.bwfinalboss.model.Indirizzo;
import it.epicode.bwfinalboss.service.IndirizzoService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/indirizzi")
public class IndirizzoController {

    @Autowired
    private IndirizzoService indirizzoService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Indirizzo createIndirizzo(@RequestBody @Validated IndirizzoDto indirizzoDto,
                                     BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(e -> e.getDefaultMessage())
                    .reduce("", (s1, s2) -> s1 + " " + s2);
            throw new ValidationException(errorMessage.trim());
        }

        return indirizzoService.creaIndirizzo(indirizzoDto);
    }

}
