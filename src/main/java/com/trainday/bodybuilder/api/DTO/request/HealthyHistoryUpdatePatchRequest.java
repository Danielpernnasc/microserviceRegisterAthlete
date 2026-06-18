package com.trainday.bodybuilder.api.DTO.request;

import com.trainday.bodybuilder.domain.model.enums.Alcoholic;
import com.trainday.bodybuilder.domain.model.enums.DiseaseType;

import java.util.List;

public record HealthyHistoryUpdatePatchRequest(
        Boolean smoker,
        Alcoholic alcoholic,
        Boolean physicallyActive,
        List<DiseaseType> diseases,
        List<String> medications,
        Boolean allergies,
        List<String> whatAllergies,
        Boolean sugeries,
        List<String> whereSugeries,
        String familyHistory
) {
}
