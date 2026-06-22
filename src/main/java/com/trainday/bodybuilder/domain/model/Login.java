package com.trainday.bodybuilder.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class Login {

    @Id
    private String id;
    @Indexed(unique = true)
    private String cpf;
    @Indexed(unique = true)
    private String email;
    private LocalDate born;
    private String password;

}
