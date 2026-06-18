package com.trainday.bodybuilder.domain.model;

import com.trainday.bodybuilder.domain.model.enums.Alcoholic;
import com.trainday.bodybuilder.domain.model.enums.DiseaseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "HealtyHistory")
public class HealthyHistory {
    @Id
    private String id;

    @Indexed(unique = true)
    private String athleteCpf;

    private Boolean smoker;

    private Alcoholic alcoholic;

    private Boolean physicallyActive;

    private List<DiseaseType> disease;

    private List<String> medications;

    private boolean allergies;

    private List<String> whatAllergies;

    private boolean surgeries;

    private List<String> wheresurgeries;

    private String familyHistory;

}
