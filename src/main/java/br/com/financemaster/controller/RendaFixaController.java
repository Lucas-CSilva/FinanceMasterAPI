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

import br.com.financemaster.model.RendaFixa;
import br.com.financemaster.model.Usuario;
import br.com.financemaster.repository.IRendaFixaRepository;
import br.com.financemaster.repository.IUsuarioRepository;
import br.com.financemaster.security.SecurityFilter;
import br.com.financemaster.security.TokenService;

@Controller("RendaFixaController")
@RequestMapping(value = "/rendaFixa")
@CrossOrigin
public class RendaFixaController {

    @Autowired
    private IRendaFixaRepository rendaFixaRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<RendaFixa>> getAll(@RequestHeader ("Authorization") String authorization)
    {
        try 
        {
            String token = SecurityFilter.recoverToken(authorization);

            String login = tokenService.validateToken(token);

            Usuario user = usuarioRepository.findByLogin(login);

            List<RendaFixa> rendaFixas = (List<RendaFixa>) this.rendaFixaRepository.findByUsuario(user);

            return new ResponseEntity<List<RendaFixa>>(rendaFixas, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/{id}", produces="application/json")
    public ResponseEntity<RendaFixa> getById(@PathVariable("id") Long id) {

        try
        {
            Optional<RendaFixa> rendaFixa_get = rendaFixaRepository.findById(id);
            return new ResponseEntity<RendaFixa>(rendaFixa_get.get(),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value="/insert", produces="application/json")
    public ResponseEntity<RendaFixa> insert (
                @RequestHeader ("Authorization") String authorization,
                @RequestBody RendaFixa rendaFixa)
    {  
        try
        {
            String token = SecurityFilter.recoverToken(authorization);

            String login = tokenService.validateToken(token);

            Usuario user = usuarioRepository.findByLogin(login);

            rendaFixa.setUsuario(user);
            
            RendaFixa rendaFixaSalva = rendaFixaRepository.save(rendaFixa);
            return new ResponseEntity<>(rendaFixaSalva, HttpStatus.OK);
        }
        catch(Exception ex)
        {
            System.out.println("Error at insert new renda fixa: " + ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value="/update", produces="application/json")
    public ResponseEntity<RendaFixa> update(
                @RequestHeader ("Authorization") String authorization,
                @RequestBody RendaFixa rendaFixa)
    {
        try
        {
            String token = SecurityFilter.recoverToken(authorization);

            String login = tokenService.validateToken(token);

            Usuario user = usuarioRepository.findByLogin(login);

            rendaFixa.setUsuario(user);
            
            RendaFixa newRendaFixa = rendaFixaRepository.save(rendaFixa);
            return new ResponseEntity<RendaFixa>(newRendaFixa, HttpStatus.OK);
        }
        catch(Exception e)
        {
            System.out.println("Error at update new renda fixa: "+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)
    {
        try
        {
            rendaFixaRepository.deleteById(id);
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
