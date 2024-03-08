package com.enigma.wmb_api.specification;

import com.enigma.wmb_api.entity.MTable;
import org.springframework.data.jpa.domain.Specification;

public class TableSpecification {
    public static Specification<MTable> getSpecification() {
        return Specification.where(null);
    }
}
