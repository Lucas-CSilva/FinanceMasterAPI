package br.com.financemaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.financemaster.repository.IUsuarioRepository;

@RestController
@RequestMapping("usuario")
public class UsuarioController {
    
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @DeleteMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)
    {
        try
        {
            usuarioRepository.deleteById(id);
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
