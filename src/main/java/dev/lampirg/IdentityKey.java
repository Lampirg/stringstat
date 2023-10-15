package dev.lampirg;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class IdentityKey {

    public static int EMPTY = -1;

    private long value;
    private int index;

    private IdentityKey(long value, int index) {
        this.value = value;
        this.index = index;
    }

    public boolean isEmpty() {
        return value == EMPTY;
    }

    public static IdentityKey of(long value, int index) {
        return new IdentityKey(value, index);
    }

    @Override
    public String toString() {
        if (value == EMPTY) {
            return "\"\"";
        }
        return String.format("\"%d\"", value);
    }
}
