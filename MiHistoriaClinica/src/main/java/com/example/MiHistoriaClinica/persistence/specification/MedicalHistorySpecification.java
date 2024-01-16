package com.example.MiHistoriaClinica.persistence.specification;

import com.example.MiHistoriaClinica.presentation.dto.MedicalHistoryDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class MedicalHistorySpecification implements Specification<MedicalHistoryDTO> {

    @Override
    public Specification<MedicalHistoryDTO> and(Specification<MedicalHistoryDTO> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<MedicalHistoryDTO> or(Specification<MedicalHistoryDTO> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<MedicalHistoryDTO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
