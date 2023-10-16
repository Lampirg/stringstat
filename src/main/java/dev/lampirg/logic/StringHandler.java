package dev.lampirg.logic;

import dev.lampirg.logic.entities.Group;
import dev.lampirg.logic.entities.line.Column;
import dev.lampirg.logic.entities.line.Line;
import lombok.SneakyThrows;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;

public class StringHandler {

    private StringHandler() {
    }

    @SneakyThrows
    public static List<Group> getStat(Supplier<Stream<String>> lines) {
        Set<Line> strings = LinesExtractor.extractLines(lines);
        Map<Column, Line> keys = formGroups(strings);
        Map<Line, Group> groupByLineLeader = createGroupsFromLeaders(keys);
        Set<Group> groupsFromEmpty = new HashSet<>();
        strings.forEach(line -> {
            if (groupByLineLeader.containsKey(line.getLeader())) {
                groupByLineLeader.get(line.getLeader()).add(line);
            } else {
                groupsFromEmpty.add(Group.fromSingleValue(line));
            }
        });
        return Stream.of(groupByLineLeader.values(), groupsFromEmpty)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparingInt(Group::size).reversed())
                .collect(toCollection(ArrayList::new));
    }

    private static Map<Column, Line> formGroups(Set<Line> strings) {
        Map<Column, Line> keys = new HashMap<>();
        strings.forEach(line -> linkLineToGroup(keys, line));
        return keys;
    }

    private static void linkLineToGroup(Map<Column, Line> keys, Line line) {
        for (Column key : line.getColumns()) {
            if (key.isEmpty()) {
                continue;
            }
            if (keys.containsKey(key)) {
                line.setLeader(keys.get(key).getLeader());
            } else {
                keys.put(key, line);
            }
        }
    }

    private static Map<Line, Group> createGroupsFromLeaders(Map<Column, Line> keys) {
        return keys.values().stream()
                .filter(line -> line.getLeader() == line)
                .distinct()
                .collect(toMap(
                        line -> line,
                        Group::fromSingleValue
                ));
    }
}
