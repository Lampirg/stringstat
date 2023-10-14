package dev.lampirg;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class StringHandler {


    @SneakyThrows
    public static List<Group> getStat(Stream<String> lines) {
        Set<List<IdentityKey>> strings = lines
                .distinct()
                .map(s -> s.split(";"))
                .filter(StringHandler::isValid)
                .map(StringHandler::toKeys)
                .collect(toCollection(HashSet::new));

        Map<IdentityKey, StringsAndKeys> keysMap = new HashMap<>();
        for (List<IdentityKey> string : strings) {
            StringsAndKeys all = new StringsAndKeys(new LinkedStrings(string));
            for (IdentityKey key : string) {
                if (keysMap.containsKey(key)) {
                    all.add(keysMap.get(key));
                }
            }
            for (IdentityKey key : all.keys) {
                keysMap.putIfAbsent(key, all);
            }
        }
        Set<Group> groups = new HashSet<>();
        for (StringsAndKeys stringsAndKeys : keysMap.values()) {
            groups.add(Group.from(stringify(stringsAndKeys.linkedStrings)));
        }
        return new ArrayList<>(groups);
    }

    private static void fillGroup(Map<IdentityKey, Set<LinkedStrings>> keysMap, Set<LinkedStrings> linkedStrings, Set<LinkedStrings> group) {
        for (LinkedStrings ls : linkedStrings) {
            group.add(ls);
            for (IdentityKey key : ls.string) {
                if (key.isEmpty() || keysMap.get(key) == linkedStrings) {
                    continue;
                }
                Set<LinkedStrings> toAdd = new HashSet<>(keysMap.get(key));
                keysMap.get(key).clear();
                fillGroup(keysMap, toAdd, group);
            }
        }
    }

    private static boolean isValid(String[] arr) {
        for (String s : arr) {
            if (s.substring(1, s.length() - 1).contains("\"")) {
                return false;
            }
        }
        return true;
    }

    private static List<IdentityKey> toKeys(String[] arr) {
        List<IdentityKey> keys = new ArrayList<>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals("\"\"")) {
                keys.add(IdentityKey.of(IdentityKey.EMPTY, i));
                continue;
            }
            keys.add(IdentityKey.of(
                    Long.parseLong(arr[i].substring(1, arr[i].length() - 1)),
                    i
            ));
        }
        return keys;
    }

    private static Collection<String> stringify(Collection<LinkedStrings> values) {
        return values.stream().map(LinkedStrings::toString).collect(toSet());
    }

    static class StringsAndKeys {

        Collection<LinkedStrings> linkedStrings = new HashSet<>();
        Collection<IdentityKey> keys = new HashSet<>();
        LinkedList<StringsAndKeys> others = new LinkedList<>();

        public StringsAndKeys(LinkedStrings linkedString) {
            linkedStrings.add(linkedString);
            keys.addAll(linkedString.string);
        }

        public void add(StringsAndKeys stringsAndKeys) {
            linkedStrings.addAll(stringsAndKeys.linkedStrings);
            keys.addAll(stringsAndKeys.keys);
        }
    }

    static class LinkedStrings {

        @AllArgsConstructor
        @EqualsAndHashCode
        static class Root {
            Collection<LinkedStrings> values;
        }

        final List<IdentityKey> string;
        Collection<LinkedStrings> values;

        public LinkedStrings(List<IdentityKey> string) {
            this.string = string;
            values = new HashSet<>();
            values.add(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LinkedStrings that = (LinkedStrings) o;
            return string.equals(that.string);
        }

        @Override
        public int hashCode() {
            return Objects.hash(string);
        }

        @Override
        public String toString() {
            return string.stream().map(IdentityKey::toString).collect(joining(";"));
        }
    }
}
