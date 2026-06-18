package com.trainday.bodybuilder.application.service;



import java.util.Optional;

import com.trainday.bodybuilder.domain.model.enums.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trainday.bodybuilder.api.DTO.request.LoginRequest;
import com.trainday.bodybuilder.api.DTO.response.LoginResponse;
import com.trainday.bodybuilder.domain.model.Athlete;
import com.trainday.bodybuilder.domain.model.Login;
import com.trainday.bodybuilder.domain.repository.AthleteRepository;
import com.trainday.bodybuilder.domain.repository.LoginRepository;
import com.trainday.bodybuilder.infra.Service.JwtService;

@Service
public class LoginService {

    private final LoginRepository loginRepository;
    private final AthleteRepository athleteRepository;
    private final JwtService jwtservice;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public LoginService(
        LoginRepository loginRepository,
        AthleteRepository athleteRepository,
        JwtService jwtservice,
       AuthenticationManager authenticationManager,
       PasswordEncoder passwordEncoder
    ){
        this.loginRepository = loginRepository;
        this.athleteRepository = athleteRepository;
        this.jwtservice = jwtservice;
        this.authenticationManager = authenticationManager; 
        this.passwordEncoder =  passwordEncoder;
    }


    public LoginResponse createLogin(LoginRequest loginRequest ){
        Login login = new Login();
        login.setEmail(loginRequest.email());
        login.setPassword(passwordEncoder.encode(loginRequest.password()));
         Login saved = loginRepository.save(login);
        return new LoginResponse(
          saved.getId(),
          saved.getEmail()
        );

        
    }


   public String authenticate(LoginRequest request) {


    try {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
            )
        );

    } catch (BadCredentialsException ex) {

        throw new BadCredentialsException("Invalid email or password");
    }



    Login user = loginRepository.findByEmail(request.email())
            .orElseThrow(() ->
                new UsernameNotFoundException("User not found")
            );

    Optional<Athlete> athletOpt = athleteRepository.findByUserId(user.getId());

    String athleteId = athletOpt
            .map(Athlete::getId)
            .orElse(null);
    Role role = athletOpt
            .map(Athlete::getRole)
            .orElse(Role.ATHLETE);



    return jwtservice.generateToken(
            user.getEmail(),
            user.getId(),
            athleteId,
            role
    );
}



}
