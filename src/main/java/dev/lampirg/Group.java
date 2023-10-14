package dev.lampirg;

import lombok.Data;

import java.util.*;

@Data
public class Group {
    private Collection<String> strings;

    private Group(Collection<String> strings) {
        this.strings = strings;
    }

    public static Group getInstance() {
        return new Group(new HashSet<>());
    }

    public static Group fromSingleValue(String string) {
        Collection<String> single = new HashSet<>();
        single.add(string);
        return new Group(single);
    }

    public static Group from(Collection<String> strings) {
        return new Group(new HashSet<>(strings));
    }


    public boolean containsAny(Collection<String> collection) {
        return collection.stream().anyMatch(s -> strings.contains(s));
    }

}
