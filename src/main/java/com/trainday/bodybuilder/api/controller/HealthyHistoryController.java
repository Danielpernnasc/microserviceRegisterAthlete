package com.trainday.bodybuilder.api.controller;

import com.trainday.bodybuilder.api.DTO.request.HealthyHistoryRequest;
import com.trainday.bodybuilder.api.DTO.request.HealthyHistoryUpdatePatchRequest;
import com.trainday.bodybuilder.application.service.HealthyHistoryService;
import com.trainday.bodybuilder.domain.model.HealthyHistory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/HealthyHistory")
public class HealthyHistoryController {

    private final HealthyHistoryService hhservice;

   public HealthyHistoryController(HealthyHistoryService hhservice){
       this.hhservice = hhservice;
   }

   @PostMapping
    public ResponseEntity<HealthyHistory> save(
           @RequestBody HealthyHistoryRequest request,
           Authentication authentication
           ){
       String athleteId = authentication.getName();
       HealthyHistory createHH = hhservice.createPacient(request, athleteId);
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(createHH);
   }

   @GetMapping("/cpf/{athleteCpf}")
    public ResponseEntity<HealthyHistory> searchCpf(
            @PathVariable String athleteCpf
   ){
       return ResponseEntity.ok(hhservice.findByHHCPF(athleteCpf));
   }

   @PutMapping("/cpf/{athleteCpf}")
    public ResponseEntity<HealthyHistory> updateHH(
            @PathVariable String athleteCpf,
            @RequestBody HealthyHistoryUpdatePatchRequest hhrequest
   ){
       return ResponseEntity.ok(
               hhservice.updateHH(athleteCpf, hhrequest)
       );

   }

   @PatchMapping("/cpf/{athleteCpf}")
    public HealthyHistory patchHH(
           @PathVariable String athleteCpf,
           @RequestBody HealthyHistoryUpdatePatchRequest hhrequest
   ){
       return hhservice.pathHH(athleteCpf, hhrequest);
   }






}
