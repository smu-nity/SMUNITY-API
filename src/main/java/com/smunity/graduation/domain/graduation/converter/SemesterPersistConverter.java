package com.smunity.graduation.domain.graduation.converter;

import com.smunity.graduation.domain.graduation.entity.type.Semester;
import jakarta.persistence.AttributeConverter;

public class SemesterPersistConverter implements AttributeConverter<Semester, String> {
    @Override
    public String convertToDatabaseColumn(Semester attribute) {

        return attribute == null ? null : attribute.getSemester();
    }

    @Override
    public Semester convertToEntityAttribute(String dbData) {

        return dbData == null ? null : Semester.findBy(dbData);
    }
}
