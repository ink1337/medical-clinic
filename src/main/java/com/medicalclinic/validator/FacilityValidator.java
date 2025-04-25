package com.medicalclinic.validator;

import com.medicalclinic.exception.ProcessingFacilityException;
import com.medicalclinic.model.dto.facility.FacilityCreateCommand;
import com.medicalclinic.model.dto.facility.FacilitySimpleDTO;
import com.medicalclinic.model.entity.Facility;
import com.medicalclinic.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

@Component
@RequiredArgsConstructor
public class FacilityValidator {
    private final FacilityRepository facilityRepository;

    public Facility validateAndGetFacilityToUpdate(FacilityCreateCommand newFacility, String referencedName) {
        var newFacilityName = newFacility.getName();
        var existingFacility = facilityRepository.findByName(referencedName)
                .orElseThrow(() -> new ProcessingFacilityException(getMessage("facility.not_found", referencedName)));

        if (!Objects.equals(referencedName, newFacilityName)) {
            validateIfNameIsValid(newFacilityName);
        }
        return existingFacility;
    }

    public void validateFacilityForPersist(FacilityCreateCommand facility) {
        checkIfFacilityWithNameExists(facility.getName());
        validateNoneNullFields(facility);
    }

    public void checkIfFacilityWithNameExists(String name) {
        if (facilityRepository.findByName(name).isPresent()) {
            throw new ProcessingFacilityException(getMessage("facility.already_exists", name));
        }
    }

    public void validateSimpleFacilities(List<FacilitySimpleDTO> facilities) {
        facilities.forEach(this::validateNoneNullFields);
    }

    private void validateIfNameIsValid(String name) {
        if (facilityRepository.findByName(name).isPresent()) {
            throw new ProcessingFacilityException(getMessage("facility.already_exists", name));
        }
    }

    private void validateNoneNullFields(FacilityCreateCommand facility) {
        if (facility.getName() == null
                || facility.getCity() == null
                || facility.getStreet() == null
                || facility.getBuildingNumber() == null
                || facility.getPostCode() == null) {
            throw new ProcessingFacilityException(getMessage("facility.all_field_must_be_set", facility.getName()));
        }
    }

    private void validateNoneNullFields(FacilitySimpleDTO facility) {
        if (facility.getName() == null
                || facility.getCity() == null
                || facility.getStreet() == null
                || facility.getBuildingNumber() == null
                || facility.getPostCode() == null) {
            throw new ProcessingFacilityException(getMessage("facility.all_field_must_be_set", facility.getName()));
        }
    }
}