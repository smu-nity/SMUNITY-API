package com.smunity.graduation.domain.graduation.converter;

import com.smunity.graduation.domain.graduation.entity.type.Grade;
import jakarta.persistence.AttributeConverter;

public class GradePersistConverter implements AttributeConverter<Grade, String> {
    @Override
    public String convertToDatabaseColumn(Grade attribute) {

        return attribute == null ? null : attribute.getGrade();
    }

    @Override
    public Grade convertToEntityAttribute(String dbData) {

        return dbData == null ? null : Grade.findBy(dbData);
    }
}