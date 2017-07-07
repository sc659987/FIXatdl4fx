package org.atdl4j.fixatdl.ui.fx8.utils;

import javafx.util.StringConverter;

import java.util.LinkedList;
import java.util.List;

public class ListStringConverter extends StringConverter<Double> {

    private double SINGLE_CHAR_WIDTH = 7;

    private List<String> values = new LinkedList<>();

    public ListStringConverter(List<String> values) {
        this.values = values;
    }

    @Override
    public String toString(Double object) {
        return this.values.get(object.intValue());
    }

    @Override
    public Double fromString(String string) {
        return (double) this.values.indexOf(string);
    }

    public double length() {
        return values.parallelStream()
                .map(String::length)
                .mapToDouble(Integer::doubleValue)
                .sum() * SINGLE_CHAR_WIDTH;
    }
}
