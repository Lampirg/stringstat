package dev.lampirg;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class Line {

    @EqualsAndHashCode.Include
    @Getter
    private final List<IdentityKey> keys;

    @EqualsAndHashCode.Exclude
    @Getter
    @Setter
    private int originalIndex;

    @EqualsAndHashCode.Exclude
    private GroupPointer pointer;

    private Line(List<IdentityKey> keys) {
        this.keys = List.copyOf(keys);
        pointer = Group.fromSingleValue(this).getPointerToThisGroup();
    }

    public static Line line(List<IdentityKey> keys) {
        return new Line(keys);
    }

    public Group group() {
        return pointer.getGroup();
    }

    public int size() {
        return keys.size();
    }


    @Override
    public String toString() {
        return keys.stream().map(IdentityKey::toString).collect(Collectors.joining(";"));
    }
}
