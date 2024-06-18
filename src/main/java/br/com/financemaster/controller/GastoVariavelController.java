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

import br.com.financemaster.model.GastoVariavel;
import br.com.financemaster.model.Usuario;
import br.com.financemaster.repository.GastoVariavelRepository;
import br.com.financemaster.repository.IUsuarioRepository;
import br.com.financemaster.security.SecurityFilter;
import br.com.financemaster.security.TokenService;


@Controller("GastoVariavelController")
@RequestMapping(value = "/gastoVariavel")
@CrossOrigin
public class GastoVariavelController{
    
    @Autowired
    private GastoVariavelRepository gastoVariavelRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<GastoVariavel>> getAll(@RequestHeader ("Authorization") String authorization)
    {
        try 
        {
            String token = SecurityFilter.recoverToken(authorization);

            String login = tokenService.validateToken(token);

            Usuario user = usuarioRepository.findByLogin(login);
            
            List<GastoVariavel> gastoVariavels = (List<GastoVariavel>) this.gastoVariavelRepository.findByUsuario(user);

            return new ResponseEntity<List<GastoVariavel>>(gastoVariavels, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/{id}", produces="application/json")
    public ResponseEntity<GastoVariavel> getById(@PathVariable("id") Long id) {

        try
        {
            Optional<GastoVariavel> gastoVariavel_get = gastoVariavelRepository.findById(id);
            return new ResponseEntity<GastoVariavel>(gastoVariavel_get.get(),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value="/insert", produces="application/json")
    public ResponseEntity<GastoVariavel> insert (
                    @RequestHeader ("Authorization") String authorization,
                    @RequestBody GastoVariavel gastoFixo)
    {  
        try
        {
            String token = SecurityFilter.recoverToken(authorization);

            String login = tokenService.validateToken(token);

            Usuario user = usuarioRepository.findByLogin(login);

            gastoFixo.setUsuario(user);
            
            GastoVariavel gastoFixoSalvo = gastoVariavelRepository.save(gastoFixo);
            return new ResponseEntity<>(gastoFixoSalvo, HttpStatus.OK);
        }
        catch(Exception ex)
        {
            System.out.println("Error at insert new gastoVariavel: " + ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value="/update", produces="application/json")
    public ResponseEntity<GastoVariavel> update(
                @RequestHeader ("Authorization") String authorization,
                @RequestBody GastoVariavel gastoVariavel)
    {
        try
        {
            String token = SecurityFilter.recoverToken(authorization);

            String login = tokenService.validateToken(token);

            Usuario user = usuarioRepository.findByLogin(login);

            gastoVariavel.setUsuario(user);
            
            GastoVariavel newGastoVariavel = gastoVariavelRepository.save(gastoVariavel);
            return new ResponseEntity<GastoVariavel>(newGastoVariavel, HttpStatus.OK);
        }
        catch(Exception e)
        {
            System.out.println("Error at update new gastoVariavel: "+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)
    {
        try
        {
            gastoVariavelRepository.deleteById(id);
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}