package dev.lampirg.logic.entities.line;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class Line {

    @EqualsAndHashCode.Include
    @Getter
    private final List<Column> columns;

    @EqualsAndHashCode.Exclude
    private Pointer pointer;

    private Line(List<Column> columns) {
        this.columns = List.copyOf(columns);
        pointer = Pointer.empty();
    }

    public static Line line(List<Column> keys) {
        return new Line(keys);
    }

    public Line getGroup() {
        if (pointer.getPointedLine() == null) {
            return this;
        }
        Line line = pointer.getPointedLine();
        while (line.pointer.getPointedLine() != null) {
            line = line.pointer.getPointedLine();
        }
        pointer.setPointedLine(line);
        return line;
    }

    public void setGroup(Line line) {
        if (pointer.getPointedLine() != null) {
            pointer.getPointedLine().setGroup(line);
        }
        pointer.setPointedLine(line);
    }

    public int size() {
        return columns.size();
    }


    @Override
    public String toString() {
        return columns.stream().map(Column::toString).collect(Collectors.joining(";"));
    }
}
