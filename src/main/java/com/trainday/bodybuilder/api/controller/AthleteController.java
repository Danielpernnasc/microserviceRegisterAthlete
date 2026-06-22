package com.trainday.bodybuilder.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainday.bodybuilder.api.DTO.request.AthleteRequest;
import com.trainday.bodybuilder.api.DTO.response.AthleteResponse;
import com.trainday.bodybuilder.application.service.AthleteService;
import com.trainday.bodybuilder.domain.model.Athlete;
import com.trainday.bodybuilder.infra.Service.JwtService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/athlete")
@SecurityRequirement(name = "bearerAuth")
public class AthleteController {


    private final AthleteService service;


    public AthleteController(
        AthleteService service,
        JwtService jwtService
    ){
        this.service = service;
    }


  @PostMapping
    public ResponseEntity<Athlete> save(
        @RequestBody AthleteRequest req,
        Authentication authentication
    ) {
      System.out.println("CHEGOU NO CONTROLLER");
        String athleteId = authentication.getName();

        Athlete createdAthlete = service.createAthlete(req, athleteId);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(createdAthlete);
    }


    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<AthleteResponse> findByCpf(
        @PathVariable String cpf
    ){
        AthleteResponse athlete = service.findbyCpf(cpf);

        return ResponseEntity.ok(new AthleteResponse(
            athlete.id(),
            athlete.cpf(),
            athlete.name(),
            athlete.socialName(),
            athlete.email(),
            athlete.born(),
            athlete.gender(),
            athlete.identity(),
            athlete.height(),
            athlete.weight()
        ));
    }


    @PutMapping("/{id}")
    public Athlete updateAthlete(
        @PathVariable String id,
        @RequestBody AthleteRequest updateAthlete
    ){
        return service.updateAthlete(id, updateAthlete);
    }

    @PatchMapping("/{id}")
    public Athlete patchAthlete(
        @PathVariable String id,
        @RequestBody AthleteRequest patchAthlete
    ){
        return service.pathAthlete(id, patchAthlete);
    }

    @DeleteMapping("/{id}")
    public void deleteAhtlete(@PathVariable String id){
        service.deleteAthlete(id);
    }

}

