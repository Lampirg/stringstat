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
    private Pointer leader;

    private Line(List<Column> columns) {
        this.columns = List.copyOf(columns);
        leader = Pointer.empty();
    }

    public static Line line(List<Column> keys) {
        return new Line(keys);
    }

    public Line getLeader() {
        if (leader.getPointedLine() == null) {
            return this;
        }
        Line line = leader.getPointedLine();
        while (line.leader.getPointedLine() != null) {
            line = line.leader.getPointedLine();
        }
        leader.setPointedLine(line);
        return line;
    }

    public void setLeader(Line line) {
        if (leader.getPointedLine() != null) {
            leader.getPointedLine().setLeader(line);
        }
        leader.setPointedLine(line);
    }

    public int size() {
        return columns.size();
    }


    @Override
    public String toString() {
        return columns.stream().map(Column::toString).collect(Collectors.joining(";"));
    }
}
