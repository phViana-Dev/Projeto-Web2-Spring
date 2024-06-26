package br.ufrn.imd.universitymanagement.controller;

import br.ufrn.imd.universitymanagement.dto.AuthenticationDTO;
import br.ufrn.imd.universitymanagement.dto.LoginResponseDTO;
import br.ufrn.imd.universitymanagement.dto.RegistroDTO;
import br.ufrn.imd.universitymanagement.model.UserEntity;
import br.ufrn.imd.universitymanagement.repository.UserRepository;
import br.ufrn.imd.universitymanagement.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity cadastrar(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        var password = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(password);

        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Object> register (@RequestBody @Valid RegistroDTO registroDTO){
        if (this.userRepository.findByLogin(registroDTO.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registroDTO.password());
        UserEntity user = new UserEntity(registroDTO.login(), encryptedPassword, registroDTO.role());

        userRepository.save(user);

        return ResponseEntity.ok().body("Registrado com sucesso");
    }

}
