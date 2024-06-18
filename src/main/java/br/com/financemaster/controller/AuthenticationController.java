package br.com.financemaster.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.financemaster.model.Usuario;
import br.com.financemaster.model.DTO.AuthenticationDTO;
import br.com.financemaster.model.DTO.LoginResponseDTO;
import br.com.financemaster.model.DTO.RegisterDTO;
import br.com.financemaster.repository.IUsuarioRepository;
import br.com.financemaster.security.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data)
    {
        
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        
        try
        {
            var auth  = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((Usuario)auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        }
        catch(Exception e)
        {
            System.out.println("Erro: " + e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data){
         
        
        if(this.usuarioRepository.findByLogin(data.login()) != null) 
        {
            return ResponseEntity.badRequest().build();
        }
        
        
        
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuario newUser = new Usuario(data.login(), encryptedPassword, data.nome(), data.role());
        this.usuarioRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

}
