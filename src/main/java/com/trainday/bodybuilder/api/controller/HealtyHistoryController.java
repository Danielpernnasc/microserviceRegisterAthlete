package com.trainday.bodybuilder.api.controller;

import com.trainday.bodybuilder.api.DTO.request.HealtyHistoryRequest;
import com.trainday.bodybuilder.api.DTO.request.HealtyHistoryUpdatePatchRequest;
import com.trainday.bodybuilder.application.service.HealthyHistoryService;
import com.trainday.bodybuilder.domain.model.HealthyHistory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/HealtyHistory")
public class HealtyHistoryController {

    private final HealthyHistoryService hhservice;

   public HealtyHistoryController(HealthyHistoryService hhservice){
       this.hhservice = hhservice;
   }

   @PostMapping
    public ResponseEntity<HealthyHistory> save(
           @RequestBody HealtyHistoryRequest request
           ){
       HealthyHistory createHH = hhservice.createPacient(request);
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(createHH);
   }

   @GetMapping("/cpf/{athleteCpf}")
    public ResponseEntity<HealthyHistory> searchCpf(
            @PathVariable String athleteCpf
   ){
       return ResponseEntity.ok(hhservice.findByAthleteCPF(athleteCpf));
   }

   @PutMapping("/cpf/{athleteCpf}")
    public ResponseEntity<HealthyHistory> updateHH(
            @PathVariable String athleteCpf,
            @RequestBody HealtyHistoryUpdatePatchRequest hhrequest
   ){
       return ResponseEntity.ok(
               hhservice.updateHH(athleteCpf, hhrequest)
       );

   }

   @PatchMapping("/cpf/{athleteCpf}")
    public HealthyHistory patchHH(
           @PathVariable String athleteCpf,
           @RequestBody HealtyHistoryUpdatePatchRequest hhrequest
   ){
       return hhservice.pathHH(athleteCpf, hhrequest);
   }






}
