package com.lineate.presentationvotingservice.controller;

import com.lineate.presentationvotingservice.dto.AuthenticationRequestDto;
import com.lineate.presentationvotingservice.entity.Client;
import com.lineate.presentationvotingservice.repository.ClientRepository;
import com.lineate.presentationvotingservice.security.JwtAuthenticationException;
import com.lineate.presentationvotingservice.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final ClientRepository clientRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    ClientRepository clientRepository,
                                    JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.clientRepository = clientRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        try {
            String login = authenticationRequestDto.getLogin();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login, authenticationRequestDto.getPassword()));
            Client client = clientRepository.getByLogin(login);
            if (client == null) {
                throw new UsernameNotFoundException("Client doesn't exists !");
            }
            String token = jwtTokenProvider.createToken(login, client.getRole().name());
            Map<Object, Object> response = Map.of(
                    "login", login,
                    "token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>("Invalid login/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}