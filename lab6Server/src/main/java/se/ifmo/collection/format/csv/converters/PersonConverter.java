package se.ifmo.collection.format.csv.converters;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import se.ifmo.collection.models.Color;
import se.ifmo.collection.models.Location;
import se.ifmo.collection.models.Person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PersonConverter extends AbstractBeanField<Person, String> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException {
        if (value == null || value.isEmpty()) {
            return null;
        }
        String[] tokens = value.split(",");
        if (tokens.length != 6 || tokens[0].isEmpty()) {
            throw new CsvDataTypeMismatchException("Invalid Person Data");
        }

        Person person = new Person();
        person.setName(tokens[0]);
        try {
            person.setBirthday(LocalDate.parse(tokens[1], formatter));
        } catch (Exception e) {
            System.err.println("Invalid date format | yyyy-MM-dd");
            person.setBirthday(LocalDate.now());
        }
        person.setEyeColor(Color.valueOf(tokens[2]));
        Location location = new Location();
        location.setX(Long.parseLong(tokens[3]));
        location.setY(Integer.parseInt(tokens[4]));
        location.setName(tokens[5]);
        person.setLocation(location);
        return person;
    }
}
