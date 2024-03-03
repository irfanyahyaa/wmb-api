package com.enigma.wmb_api.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EntityUtil {
    public List<String> getValidSortFields(Class<?> entity) {
        List<String> validSortField = new ArrayList<>();

        Field[] fields = entity.getDeclaredFields();

        for (Field field : fields) {
            validSortField.add(field.getName());
        }

        return validSortField;
    }
}
