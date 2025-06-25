package it.epicode.bwfinalboss.controller;

import it.epicode.bwfinalboss.dto.LoginDto;
import it.epicode.bwfinalboss.dto.UtenteDto;
import it.epicode.bwfinalboss.exception.AlreadyExistException;
import it.epicode.bwfinalboss.exception.NotFoundException;
import it.epicode.bwfinalboss.exception.ValidationException;
import it.epicode.bwfinalboss.model.Utente;
import it.epicode.bwfinalboss.service.AuthService;
import it.epicode.bwfinalboss.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public Utente register(@RequestBody @Validated UtenteDto utenteDto, BindingResult bindingResult) throws ValidationException,  AlreadyExistException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(s, e)->s+e));
        }
        return  utenteService.saveUtente(utenteDto);
    }

    @PostMapping("/auth/login")
    public String login(@RequestBody @Validated LoginDto loginDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(s,e)->s+e));
        }
        return authService.login(loginDto);
    }
}