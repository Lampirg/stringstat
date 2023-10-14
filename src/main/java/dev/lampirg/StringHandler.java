package dev.lampirg;

import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class StringHandler {

    private StringHandler() {
        throw new IllegalStateException("Utility class");
    }


    @SneakyThrows
    public static List<Group> getStat(Supplier<Stream<String>> lines) {
        AtomicInteger integer = new AtomicInteger(0);
        Set<Line> strings = lines.get()
                .distinct()
                .map(s -> s.split(";"))
                .filter(StringHandler::isValid)
                .map(StringHandler::toKeys)
                .map(Line::line)
                .map(line -> {
                    line.setOriginalIndex(integer.getAndIncrement());
                    return line;
                })
                .collect(toCollection(HashSet::new));
        Set<Group> groups = strings.stream().map(Line::group).collect(toCollection(HashSet::new));
        for (int i = 0; i < Collections.max(strings, Comparator.comparingInt(Line::size)).size(); i++) {
            Map<IdentityKey, Group> keys = new HashMap<>();
            for (Line line : strings) { // for group : groups
                if (i >= line.size()) {
                    continue;
                }
                IdentityKey key = line.getKeys().get(i);
                if (keys.containsKey(key)) {
                    if (keys.get(key).equals(line.group())) {
                        continue;
                    }
                    groups.remove(line.group());
                    keys.get(key).addGroup(line.group());
                }
                keys.putIfAbsent(key, line.group());
            }
        }
        return new ArrayList<>(groups);
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
}
