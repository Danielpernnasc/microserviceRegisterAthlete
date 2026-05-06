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

}
