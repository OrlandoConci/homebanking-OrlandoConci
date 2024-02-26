package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoginDTO;
import com.mindhub.homebanking.dtos.RegisterDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginDTO loginDTO) {
        if(loginDTO.email().isBlank()) {
            return new ResponseEntity<>("The email field must not be empty " , HttpStatus.FORBIDDEN);
        }
        if(loginDTO.password().isBlank()) {
            return new ResponseEntity<>("The password field must not be empty " , HttpStatus.FORBIDDEN);
        }

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.email());
            final String jwt = jwtUtilService.generateToken(userDetails);
            return ResponseEntity.ok(jwt);
        }catch (Exception e) {
            return new ResponseEntity<>("Error logging in, try again", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {

        if(!registerDTO.email().contains("@")) {
            return new ResponseEntity<>("Email must include \"@\" ", HttpStatus.FORBIDDEN);
        }
        if(registerDTO.firstName().isBlank()) {
            return new ResponseEntity<>("The name field must not be empty " , HttpStatus.FORBIDDEN);
        }
        if(registerDTO.lastName().isBlank()) {
            return new ResponseEntity<>("The last name field must not be empty " , HttpStatus.FORBIDDEN);
        }
        if(registerDTO.email().isBlank()) {
            return new ResponseEntity<>("The email field must not be empty " , HttpStatus.FORBIDDEN);
        }
        if(registerDTO.password().isBlank()) {
            return new ResponseEntity<>("The password field must not be empty " , HttpStatus.FORBIDDEN);
        }

        Client client = new Client(
                registerDTO.firstName(),
                registerDTO.lastName(),
                registerDTO.email(),
                passwordEncoder.encode(registerDTO.password())
        );
        clientRepository.save(client);
        return ResponseEntity.ok(client);
    }
}
