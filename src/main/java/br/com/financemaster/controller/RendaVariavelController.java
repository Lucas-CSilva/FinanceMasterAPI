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

import br.com.financemaster.model.RendaVariavel;
import br.com.financemaster.model.Usuario;
import br.com.financemaster.repository.IRendaVariavelRepository;
import br.com.financemaster.repository.IUsuarioRepository;
import br.com.financemaster.security.SecurityFilter;
import br.com.financemaster.security.TokenService;

@Controller("RendaVariavelController")
@RequestMapping(value = "/rendaVariavel")
@CrossOrigin
public class RendaVariavelController{

    @Autowired
    private IRendaVariavelRepository rendaVariavelRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<RendaVariavel>> getAll(@RequestHeader ("Authorization") String authorization)
    {
        try 
        {
            String token = SecurityFilter.recoverToken(authorization);

            String login = tokenService.validateToken(token);

            Usuario user = usuarioRepository.findByLogin(login);

            List<RendaVariavel> rendaVariaveis = (List<RendaVariavel>) this.rendaVariavelRepository.findByUsuario(user);

            return new ResponseEntity<List<RendaVariavel>>(rendaVariaveis, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/{id}", produces="application/json")
    public ResponseEntity<RendaVariavel> getById(@PathVariable("id") Long id) {

        try
        {
            Optional<RendaVariavel> contaBancaria_get = rendaVariavelRepository.findById(id);
            return new ResponseEntity<RendaVariavel>(contaBancaria_get.get(),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping(value="/insert", produces="application/json")
    public ResponseEntity<RendaVariavel> insert (
                    @RequestHeader ("Authorization") String authorization,
                    @RequestBody RendaVariavel rendaVariavel)
    {  
        try
        {
            String token = SecurityFilter.recoverToken(authorization);

            String login = tokenService.validateToken(token);

            Usuario user = usuarioRepository.findByLogin(login);

            rendaVariavel.setUsuario(user);

            RendaVariavel contaBancariaSalva = rendaVariavelRepository.save(rendaVariavel);
            return new ResponseEntity<>(contaBancariaSalva, HttpStatus.OK);
        }
        catch(Exception ex)
        {
            System.out.println("Error at insert new renda variavel: " + ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value="/update", produces="application/json")
    public ResponseEntity<RendaVariavel> update(
                @RequestHeader ("Authorization") String authorization,
                @RequestBody RendaVariavel rendaVariavel)
    {
        try
        {
            String token = SecurityFilter.recoverToken(authorization);

            String login = tokenService.validateToken(token);

            Usuario user = usuarioRepository.findByLogin(login);

            rendaVariavel.setUsuario(user);

            RendaVariavel newRendaVariavel = rendaVariavelRepository.save(rendaVariavel);
            return new ResponseEntity<RendaVariavel>(newRendaVariavel, HttpStatus.OK);
        }
        catch(Exception e)
        {
            System.out.println("Error at update new renda variavel: "+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)
    {
        try
        {
            rendaVariavelRepository.deleteById(id);
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
