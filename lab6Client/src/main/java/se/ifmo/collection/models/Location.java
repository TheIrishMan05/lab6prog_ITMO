package se.ifmo.collection.models;

import lombok.Getter;
import lombok.Setter;
import se.ifmo.collection.exceptions.InvalidArgumentException;

import java.io.Serializable;

@Getter
public class Location implements Serializable {
    @Setter
    private long x;
    @Setter
    private int y;
    private String name; //Длина строки не должна быть больше 992, Поле может быть null


    @Override
    public String toString() {
        return x +
                "," + y +
                "," + name;
    }

    public void setName(String name) {
        if (name.length() > 992)
            throw new InvalidArgumentException("name", "name length has to be less than 992 characters");
        this.name = name;
    }

}