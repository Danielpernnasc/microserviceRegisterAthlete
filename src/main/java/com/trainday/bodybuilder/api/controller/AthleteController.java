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

    private static final Logger log = LoggerFactory.getLogger(AthleteService.class);
    private final JwtService jwtService;

    public AthleteController(
        AthleteService service,
        JwtService jwtService
    ){
        this.service = service;
        this.jwtService = jwtService;
    }


  @PostMapping
    public ResponseEntity<Athlete> save(
        @RequestBody AthleteRequest req,
        Authentication authentication
    ) {
        String athleteId = authentication.getName();

        Athlete createdAthlete = service.createAthlete(req, athleteId);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(createdAthlete);
    }
        @GetMapping("/{id}")
        public AthleteResponse findById(
            @PathVariable String id
        ) {
            return service.getAthleteById(id);
        
        }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<AthleteResponse> findByCpf(
        @PathVariable String cpf
    ){
        Athlete athlete = service.findbyCpf(cpf);

        return ResponseEntity.ok(new AthleteResponse(
            athlete.getId(),
            athlete.getCpf(),
            athlete.getName(),
            athlete.getEmail(),
            athlete.getAge(),
            athlete.getGender(),
            athlete.getIdentity(),
            athlete.getHeight(),
            athlete.getWeight()
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

