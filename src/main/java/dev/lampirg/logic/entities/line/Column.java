package dev.lampirg.logic.entities.line;

import lombok.EqualsAndHashCode;

import java.text.NumberFormat;
import java.util.Locale;

@EqualsAndHashCode
public class Column {

    private static NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.UK);

    static {
        numberFormat.setGroupingUsed(false);
    }

    public static final int EMPTY_VALUE = -1;

    private final double value;
    private final int index;

    private Column(double value, int index) {
        this.value = value;
        this.index = index;
    }

    public boolean isEmpty() {
        return value == EMPTY_VALUE;
    }

    public static Column of(double value, int index) {
        return new Column(value, index);
    }

    public static Column empty(int index) {
        return new Column(-1, index);
    }

    @Override
    public String toString() {
        if (value == EMPTY_VALUE) {
            return "\"\"";
        }
        return String.format("\"%s\"", numberFormat.format(value));
    }
}
