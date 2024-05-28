package com.enigma.wmb_api.specification;

import com.enigma.wmb_api.dto.request.GetMenuRequest;
import com.enigma.wmb_api.entity.MMenu;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class MenuSpecification {
    public static Specification<MMenu> getSpecification(String q) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(q)) return criteriaBuilder.conjunction();

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("menu")), "%" + q.toLowerCase() + "%"));

            try {
                Long price = Long.valueOf(q);
                predicates.add(criteriaBuilder.equal(root.get("price"), price));
            } catch (NumberFormatException e) {
                // Ignore
            }

            try {
                Integer stock = Integer.valueOf(q);
                predicates.add(criteriaBuilder.equal(root.get("stock"), stock));
            } catch (NumberFormatException e) {
                // Ignore
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[]{}));

            /*List<Predicate> predicates = new ArrayList<>();

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

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();*/
        };
    }
}