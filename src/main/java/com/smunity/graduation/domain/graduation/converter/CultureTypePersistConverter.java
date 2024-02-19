package com.smunity.graduation.domain.graduation.converter;

import com.smunity.graduation.domain.graduation.entity.type.CultureType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CultureTypePersistConverter implements AttributeConverter<CultureType, String> {
    @Override
    public String convertToDatabaseColumn(CultureType attribute) {

        return attribute == null ? null : attribute.getType();
    }

    @Override
    public CultureType convertToEntityAttribute(String dbData) {

        return dbData == null ? null : CultureType.findBy(dbData);
    }
}
