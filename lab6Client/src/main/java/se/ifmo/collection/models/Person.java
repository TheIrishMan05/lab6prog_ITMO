package se.ifmo.collection.models;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;
import se.ifmo.collection.exceptions.InvalidArgumentException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
public class Person implements Serializable {
    @CsvBindByName(required = true)
    private String name; //Поле не может быть null, Строка не может быть пустой
    @Setter
    private LocalDate birthday; //Поле может быть null
    @Setter
    private Color eyeColor; //Поле может быть null
    @Setter
    private Location location; //Поле может быть null

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(birthday, person.birthday) && eyeColor == person.eyeColor && Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, eyeColor, location);
    }

    @Override
    public String toString() {
        return name +
                "," + birthday +
                "," + eyeColor +
                "," + location;
    }


    public void setName(String name) {
        if (name.isEmpty()) throw new InvalidArgumentException("name", "name cannot be empty");
        this.name = name;
    }


}
