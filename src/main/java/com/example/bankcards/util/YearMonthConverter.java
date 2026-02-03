package com.example.bankcards.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.YearMonth;

@Converter(autoApply = true)
public class YearMonthConverter
        implements AttributeConverter<YearMonth, String> {

    @Override
    public String convertToDatabaseColumn(YearMonth ym) {
        return ym == null ? null : ym.toString(); // 2026-02
    }

    @Override
    public YearMonth convertToEntityAttribute(String db) {
        return db == null ? null : YearMonth.parse(db);
    }
}

