package br.com.financemaster.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.financemaster.model.ContaBancaria;
import br.com.financemaster.model.Usuario;
import br.com.financemaster.repository.IContaBancariaRepository;
import br.com.financemaster.repository.IUsuarioRepository;
import br.com.financemaster.security.SecurityFilter;
import br.com.financemaster.security.TokenService;

@Controller("ContaBancariaController")
@RequestMapping(value = "/contaBancaria")
@CrossOrigin
public class ContaBancariaController {

    @Autowired
    private IContaBancariaRepository contaBancariaRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<ContaBancaria>> getAll(@RequestHeader ("Authorization") String authorization)
    {
        try 
        {
            String token = SecurityFilter.recoverToken(authorization);

            String login = tokenService.validateToken(token);

            Usuario user = usuarioRepository.findByLogin(login);

            List<ContaBancaria> contaBancaria = (List<ContaBancaria>) this.contaBancariaRepository.findByUsuario(user);
            
            return new ResponseEntity<List<ContaBancaria>>(contaBancaria, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/{id}", produces="application/json")
    public ResponseEntity<ContaBancaria> getById(@PathVariable("id") Long id) {

        try
        {
            Optional<ContaBancaria> contaBancaria_get = contaBancariaRepository.findById(id);
            return new ResponseEntity<ContaBancaria>(contaBancaria_get.get(),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }   

    @PostMapping(value="/insert", produces="application/json")
    public ResponseEntity<ContaBancaria> insert (
                @RequestHeader ("Authorization") String authorization,
                @RequestBody ContaBancaria contaBancaria)
    {   
        try
        {            
            String token = SecurityFilter.recoverToken(authorization);

            String login = tokenService.validateToken(token);

            Usuario user = usuarioRepository.findByLogin(login);

            contaBancaria.setUsuario(user);

            ContaBancaria contaBancariaSalva = contaBancariaRepository.save(contaBancaria);
            return new ResponseEntity<ContaBancaria>(contaBancariaSalva, HttpStatus.OK);
        }
        catch(Exception ex)
        {
            System.out.println("Error at insert new conta bancaria: " + ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value="/update", produces="application/json")
    public ResponseEntity<ContaBancaria> update(
                @RequestHeader ("Authorization") String authorization,    
                @RequestBody ContaBancaria contaBancaria)
    {
        try
        {   
            String token = SecurityFilter.recoverToken(authorization);

            String login = tokenService.validateToken(token);

            Usuario user = usuarioRepository.findByLogin(login);

            contaBancaria.setUsuario(user);
            
            ContaBancaria newContaBancaria = contaBancariaRepository.save(contaBancaria);
            return new ResponseEntity<ContaBancaria>(newContaBancaria, HttpStatus.OK);
        }
        catch(Exception e)
        {
            System.out.println("Error at update new contaBancaria: "+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)
    {
        try
        {
            contaBancariaRepository.deleteById(id);
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
