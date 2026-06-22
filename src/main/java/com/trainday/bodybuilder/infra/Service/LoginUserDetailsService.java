package com.trainday.bodybuilder.infra.Service;




import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trainday.bodybuilder.domain.model.Login;
import com.trainday.bodybuilder.domain.repository.LoginRepository;

import java.util.Optional;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    private final LoginRepository loginRepository;
    private final static String Athlete_not_found = "Athlete not found";

    public LoginUserDetailsService(LoginRepository loginRepository){
        this.loginRepository = loginRepository;
    }

 @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Login> user = loginRepository.findByEmail(login);
        if(user.isEmpty()){
            user = loginRepository.findByCpf(login);
        }
     Login athlete = user.orElseThrow(
             () -> new UsernameNotFoundException(Athlete_not_found));



     return User.builder()
             .username(athlete.getCpf())
             .password(athlete.getPassword())
             .authorities("USER")
             .build();
    }



}
