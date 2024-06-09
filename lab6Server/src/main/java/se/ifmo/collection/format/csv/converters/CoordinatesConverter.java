package se.ifmo.collection.format.csv.converters;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import se.ifmo.collection.models.Coordinates;

public class CoordinatesConverter extends AbstractBeanField<Coordinates, String> {
    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if (value == null || value.isEmpty()) {
            return null;
        }
        String[] tokens = value.split(",");
        if (tokens.length != 2 || !tokens[0].matches("\\d+") || !tokens[1].matches("\\d+[.,]\\d+")) {
            throw new CsvDataTypeMismatchException("Invalid coordinates format");

        }
        Coordinates coordinates = new Coordinates();

        coordinates.setX(Long.parseLong(tokens[0]));
        coordinates.setY(Float.parseFloat(tokens[1]));
        return coordinates;
    }

    @Override
    public String convertToWrite(Object value) {
        Coordinates coordinates = (Coordinates) value;
        if (coordinates == null) {
            return "";
        }
        return coordinates.getX() + "," + coordinates.getY();
    }
}
