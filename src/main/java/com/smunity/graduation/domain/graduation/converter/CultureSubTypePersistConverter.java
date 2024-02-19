package com.smunity.graduation.domain.graduation.converter;

import com.smunity.graduation.domain.graduation.entity.type.CultureSubType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CultureSubTypePersistConverter implements AttributeConverter<CultureSubType, String> {
    @Override
    public String convertToDatabaseColumn(CultureSubType attribute) {

        return attribute == null ? null : attribute.getType();
    }

    @Override
    public CultureSubType convertToEntityAttribute(String dbData) {

        return dbData == null ? null : CultureSubType.findBy(dbData);
    }
}
