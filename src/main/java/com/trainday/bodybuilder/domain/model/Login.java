package com.trainday.bodybuilder.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class Login {

    @Id
    private String id;
    private String email; // usar o email da variavel que vem do Athlete
    private String password;

    // public Login(){}

    // public Login(String email, String password){
    //     this.id = id;
    //     this.email = email;
    //     this.password = password;
    // }

    // public String getId(){
    //     return id;
    // }

    // public void setId(String id){
    //     this.id = id;
    // }

    // public String getEmail(){
    //     return email;
    // }

    // public void setEmail(String email){
    //     this.email = email;
    // }

    // public String getPassword(){
    //     return password;
    // }

    // public void setPassword(String password){
    //     this.password = password;
    // }
}
