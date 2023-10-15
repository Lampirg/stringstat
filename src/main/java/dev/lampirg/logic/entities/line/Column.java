package dev.lampirg.logic.entities.line;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Column {

    public static final int EMPTY_VALUE = -1;

    private final long value;
    private final int index;

    private Column(long value, int index) {
        this.value = value;
        this.index = index;
    }

    public boolean isEmpty() {
        return value == EMPTY_VALUE;
    }

    public static Column of(long value, int index) {
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
        return String.format("\"%d\"", value);
    }
}
