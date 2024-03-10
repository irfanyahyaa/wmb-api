package com.enigma.wmb_api.specification;

import com.enigma.wmb_api.dto.request.GetBillRequest;
import com.enigma.wmb_api.entity.TBill;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BillSpecification {
    public static Specification<TBill> getSpecification(GetBillRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getTransDate() != null) {
                predicates.add(criteriaBuilder.equal(root.get("transDate"), request.getTransDate()));
            }

            if (request.getUserId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("user"), request.getUserId()));
            }

            if (request.getTableId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("table"), request.getTableId()));
            }

            if (request.getTransTypeId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("transType"), request.getTransTypeId()));
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}