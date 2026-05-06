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
    @Field("percentagefat")
    private String userId;

    // public Athlete(){}

    // public Athlete(
    //     String id, String cpf, String name, String email, Long age, Gender gender, GenderIdentity identity, Double height, Double weight, String userId
    // ){
    //     this.id = id;
    //     this.cpf = cpf;
    //     this.name = name;
    //     this.email = email;
    //     this.age = age;
    //     this.gender = gender;
    //     this.identity = identity;
    //     this.height = height;
    //     this.weight = weight;
    //     this.userId = userId;
    // }

    // public String getId(){
    //     return id;
    // }

    // public void setId(String id){
    //     this.id = id;
    // }

    // public String getCPF(){
    //     return cpf;
    // }

    // public void setCPF(String cpf){
    //     this.cpf = cpf;
    // }

    // public String getName(){
    //     return name;
    // }

    // public void setName(String name){
    //     this.name = name;
    // }

    // public String getEmail(){
    //     return email;
    // }

    // public void setEmail(String email){
    //     this.email = email;
    // }

    // public Long getAge() {
    //     return age;
    // }

    // public void setAge(long age) {
    //     this.age = age;
    // }

    // public Gender getGender(){
    //     return gender;
    // }

    // public void setGender(Gender gender){
    //     this.gender = gender;
    // }

    // public GenderIdentity getIdentity(){
    //     return identity;
    // }

    // public void setIdentity(GenderIdentity identity){
    //     this.identity = identity;
    // }


    // public Double getHeight() {
    //     return height;
    // }

    // public void setHeight(double height) {
    //     this.height = height;
    // }

    // public Double getWeight() {
    //     return weight;
    // }

    // public void setWeight(double weight){
    //     this.weight = weight;
    // }

    // public String getUserId(){
    //     return userId;
    // }

    // public void setUserId(String userId){
    //     this.userId = userId;
    // }

}
