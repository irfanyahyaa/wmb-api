package com.enigma.wmb_api.specification;

import com.enigma.wmb_api.dto.request.SearchUserRequest;
import com.enigma.wmb_api.entity.MUser;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<MUser> getSpecification(SearchUserRequest request) {
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

            if (request.getIsMember() != null) {
                Predicate memberPredicate = criteriaBuilder.equal(
                        root.get(("isMember")),
                        request.getIsMember()
                );

                predicates.add(memberPredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
