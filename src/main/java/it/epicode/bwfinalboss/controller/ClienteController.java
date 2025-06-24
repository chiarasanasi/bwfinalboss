package it.epicode.bwfinalboss.controller;

import it.epicode.bwfinalboss.dto.ClienteDto;
import it.epicode.bwfinalboss.exception.NotFoundException;
import it.epicode.bwfinalboss.model.Cliente;
import it.epicode.bwfinalboss.service.ClienteService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clienti")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente createCliente(@RequestBody @Validated ClienteDto clienteDto, BindingResult bindingResult) throws ValidationException{
         if (bindingResult.hasErrors()) {
        throw new ValidationException(bindingResult.getAllErrors()
                .stream()
                .map(e -> e.getDefaultMessage())
                .reduce("", (s1, s2) -> s1 + " " + s2));
    }
        return clienteService.saveCliente(clienteDto);
  }

    @GetMapping
    public List<Cliente> getAllClienti() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable int id) throws NotFoundException {
        return clienteService.findById(id);
    }

    @PutMapping("/{id}")
    public Cliente updateCliente(@PathVariable int id,
                                 @RequestBody @Valid ClienteDto clienteDto,
                                 BindingResult bindingResult) throws NotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors()
                    .stream()
                    .map(e -> e.getDefaultMessage())
                    .reduce("", (s1, s2) -> s1 + " " + s2));
        }
        return clienteService.updateCliente(id, clienteDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCliente(@PathVariable int id) throws NotFoundException {
        clienteService.deleteCliente(id);
    }
}
