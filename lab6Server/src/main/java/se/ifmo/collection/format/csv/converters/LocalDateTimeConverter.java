package se.ifmo.collection.format.csv.converters;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter extends AbstractBeanField<LocalDateTime, String> {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    protected Object convert(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(value, dateTimeFormatter);
        } catch (Exception e) {
            System.err.println("Invalid date format | dd/MM/yyyy HH:mm");
            return LocalDateTime.now();
        }
    }

    @Override
    public String convertToWrite(Object value) {
        LocalDateTime localDateTime = (LocalDateTime) value;
        if (localDateTime == null) {
            return "";
        }
        return localDateTime.format(dateTimeFormatter);
    }

}
