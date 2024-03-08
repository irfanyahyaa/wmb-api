package com.enigma.wmb_api.specification;

import com.enigma.wmb_api.dto.request.GetMenuRequest;
import com.enigma.wmb_api.entity.MMenu;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class MenuSpecification {
    public static Specification<MMenu> getSpecification(GetMenuRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getMenu() != null) {
                Predicate menuPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("menu")),
                        "%" + request.getMenu() + "%"
                );

                predicates.add(menuPredicate);
            }

            if (request.getMinPrice() != null && request.getMaxPrice() != null) {
                Predicate pricePredicate = criteriaBuilder.between(
                        root.get("price"), request.getMinPrice(), request.getMaxPrice()
                );

                predicates.add(pricePredicate);
            }

            if (request.getMinPrice() != null || request.getMaxPrice() != null) {
                Predicate minPricePredicate = criteriaBuilder.lessThan(
                        root.get("price"), request.getMinPrice()
                );
                predicates.add(minPricePredicate);

                Predicate maxPricePredicate = criteriaBuilder.greaterThan(
                        root.get("price"), request.getMaxPrice()
                );
                predicates.add(maxPricePredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}