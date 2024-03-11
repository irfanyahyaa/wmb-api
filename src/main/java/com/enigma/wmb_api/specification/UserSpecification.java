package com.enigma.wmb_api.specification;

import com.enigma.wmb_api.dto.request.GetUserRequest;
import com.enigma.wmb_api.entity.MUser;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<MUser> getSpecification(GetUserRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getName() != null) {
                Predicate namePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + request.getName() + "%"
                );

                predicates.add(namePredicate);
            }

            if (request.getMobilePhoneNo() != null) {
                Predicate phonePredicate = criteriaBuilder.like(
                        root.get("mobilePhoneNo"),
                        "%" + request.getMobilePhoneNo() + "%"
                );

                predicates.add(phonePredicate);
            }

            if (request.getIsActive() != null) {
                Predicate memberPredicate = criteriaBuilder.equal(
                        root.get(("isActive")),
                        request.getIsActive()
                );

                predicates.add(memberPredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}