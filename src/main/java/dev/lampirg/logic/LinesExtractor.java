package dev.lampirg.logic;

import dev.lampirg.logic.entities.line.Column;
import dev.lampirg.logic.entities.line.Line;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class LinesExtractor {

    private LinesExtractor() {
    }

    static Set<Line> extractLines(Supplier<Stream<String>> lines) {
        return lines.get()
                .distinct()
                .map(s -> s.split(";", -1))
                .filter(LinesExtractor::isValid)
                .map(LinesExtractor::toKeys)
                .map(Line::line)
                .collect(toCollection(HashSet::new));
    }

    private static boolean isValid(String[] arr) {
        for (String s : arr) {
            if (!s.isEmpty() && s.substring(1, s.length() - 1).contains("\"")) {
                return false;
            }
        }
        return true;
    }

    private static List<Column> toKeys(String[] arr) {
        List<Column> keys = new ArrayList<>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals("\"\"") || arr[i].isEmpty()) {
                keys.add(Column.empty(i));
                continue;
            }
            keys.add(Column.of(
                    Double.parseDouble(arr[i].substring(1, arr[i].length() - 1)),
                    i
            ));
        }
        return keys;
    }
}
