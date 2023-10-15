package dev.lampirg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class Group {
    private Collection<Line> lines;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private GroupPointer pointerToThisGroup;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<IdentityKey> keys;

    private Group(Collection<Line> lines) {
        this.lines = lines;
        keys = lines.stream().flatMap(line -> line.getKeys().stream()).collect(Collectors.toSet());
        pointerToThisGroup = GroupPointer.fromGroup(this);
    }

    public static Group getInstance() {
        return new Group(new HashSet<>());
    }

    public static Group fromSingleValue(Line line) {
        Collection<Line> single = new HashSet<>();
        single.add(line);
        return new Group(single);
    }

    public static Group from(Collection<Line> lines) {
        return new Group(new HashSet<>(lines));
    }

    public boolean containsKey(IdentityKey key) {
        return keys.contains(key);
    }

    public void addGroup(Group group) {
        group.pointerToThisGroup.setPointer(this);
        lines.addAll(group.getLines());
        keys.addAll(group.getKeys());
    }
}
