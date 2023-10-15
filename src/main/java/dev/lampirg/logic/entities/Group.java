package dev.lampirg.logic.entities;

import dev.lampirg.logic.entities.line.Line;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;

@Data
public class Group {
    private Collection<Line> lines;

    private Group(Collection<Line> lines) {
        this.lines = lines;

    }

    public static Group empty() {
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

    public void add(Line line) {
        lines.add(line);
    }

    public int size() {
        return lines.size();
    }

}
