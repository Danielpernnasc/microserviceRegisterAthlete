package com.trainday.bodybuilder.domain.model;

import com.trainday.bodybuilder.domain.model.enums.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import com.trainday.bodybuilder.domain.model.enums.Gender;
import com.trainday.bodybuilder.domain.model.enums.GenderIdentity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "athlete")
public class Athlete {

    @Id
    private String id;
    @Indexed(unique = true)
    private String cpf;
    private String name;
    private String socialname;
    @Indexed(unique = true)
    private String email;
    private LocalDate born;
    private Gender gender;
    private GenderIdentity identity;
    private Double height;
    private Double weight;
    private String userId;
    private Role role;

   

}
