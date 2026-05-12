package com.trainday.bodybuilder.infra.Service;




import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trainday.bodybuilder.domain.model.Login;
import com.trainday.bodybuilder.domain.repository.LoginRepository;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    private final LoginRepository loginRepository;

    public LoginUserDetailsService(LoginRepository loginRepository){
        this.loginRepository = loginRepository;
    }

 @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Login login = loginRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: "));
        

        return User
                .builder()
                .username(login.getEmail())
                .password(login.getPassword())
                .authorities("USER")
                .build();
    }



}
