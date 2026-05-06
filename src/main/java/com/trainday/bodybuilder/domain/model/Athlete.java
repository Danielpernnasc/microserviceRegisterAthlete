package com.trainday.bodybuilder.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.trainday.bodybuilder.domain.model.enums.Gender;
import com.trainday.bodybuilder.domain.model.enums.GenderIdentity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
    private String email;
    private Long age;
    private Gender gender;
    private GenderIdentity identity;
    private Double height;
    private Double weight;
    private String userId;

   

}
