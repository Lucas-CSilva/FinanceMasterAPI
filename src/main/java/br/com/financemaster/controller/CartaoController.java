package br.com.financemaster.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.financemaster.model.Cartao;
import br.com.financemaster.repository.CartaoRepository;

@Controller("CartaoController")
@RequestMapping(value = "/cartao")
public class CartaoController {
    
    @Autowired
    private CartaoRepository cartaoRepository;

    @GetMapping(value="/", produces="application/json")
    public ResponseEntity<List<Cartao>> getAll (){
        List<Cartao> cartao = (List<Cartao>) this.cartaoRepository.findAll();

        return new ResponseEntity<>(cartao, HttpStatus.OK);
    }

    @GetMapping(value="/{id}", produces="application/json")
    public ResponseEntity<Cartao> getById(@PathVariable("id") Long id) {

        try
        {
            Optional<Cartao> cartao_get = cartaoRepository.findById(id);
            return new ResponseEntity<Cartao>(cartao_get.get(),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value="/insert", produces="application/json")
    public ResponseEntity<Cartao> create (@RequestBody Cartao cartao){
        Cartao cartaoSalvo = cartaoRepository.save(cartao);

        return new ResponseEntity<>(cartaoSalvo, HttpStatus.OK);
    }

    @PutMapping(value="/update", produces="application/json")
    public ResponseEntity<Cartao> update(@RequestBody Cartao cartao)
    {
        try
        {
            Cartao newCartao = cartaoRepository.save(cartao);
            return new ResponseEntity<Cartao>(newCartao, HttpStatus.OK);
        }
        catch(Exception e)
        {
            System.out.println("Error at update new cartao: "+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)
    {
        try
        {
            cartaoRepository.deleteById(id);
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
