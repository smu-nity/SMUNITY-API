package com.smunity.graduation.domain.graduation.converter;

import com.smunity.graduation.domain.graduation.entity.type.SubjectType;
import jakarta.persistence.AttributeConverter;

public class SubjectTypePersistConverter implements AttributeConverter<SubjectType, String> {
    @Override
    public String convertToDatabaseColumn(SubjectType attribute) {

        return attribute == null ? null : attribute.getType();
    }

    @Override
    public SubjectType convertToEntityAttribute(String dbData) {

        return dbData == null ? null : SubjectType.findBy(dbData);
    }
}
