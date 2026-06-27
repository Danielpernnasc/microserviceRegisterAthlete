package com.trainday.bodybuilder.application.service;



import java.util.Optional;

import com.trainday.bodybuilder.api.DTO.request.RegisterRequest;
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


    public LoginResponse createLogin(RegisterRequest registerRequest ){
        Login login = new Login();
        login.setCpf(registerRequest.cpf());
        login.setEmail(registerRequest.email());
        login.setBorn(registerRequest.born());
        login.setPassword(passwordEncoder.encode(registerRequest.password()));
        login.setRole(registerRequest.role());
         Login saved = loginRepository.save(login);
        return new LoginResponse(
          saved.getId(),
          saved.getEmail(),
                saved.getCpf(),
                saved.getBorn(),
                saved.getRole()
        );


    }


   public String authenticate(LoginRequest request) {
       System.out.println("LOGIN RECEBIDO = " + request.login());

       Optional<Login> user = loginRepository.findByEmail(request.login());
       System.out.println("ACHOU EMAIL = " + user.isPresent());
       if (user.isEmpty()) {
           user = loginRepository.findByCpf(request.login());
           System.out.println("ACHOU CPF = " + user.isPresent());
       }
       Login login = user.orElseThrow(
               () -> new RuntimeException("Invalid login or password")
       );
       if (!passwordEncoder.matches(
               request.password(),
               login.getPassword())) {

           throw new RuntimeException("Invalid login or password");
       }

       try {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.login(),
                request.password()
            )
        );

    } catch (BadCredentialsException ex) {

        throw new BadCredentialsException("Invalid email or password");
    }



       Optional<Athlete> athleteOpt =
               athleteRepository.findByUserId(login.getId());

    String athleteId = athleteOpt
            .map(Athlete::getId)
            .orElse(null);

    Role role = athleteOpt
            .map(Athlete::getRole)
            .orElse(Role.ATHLETE);



    return jwtservice.generateToken(
            login.getEmail(),
            login.getCpf(),
            login.getId(),
            athleteId,
            role
    );
}



}
