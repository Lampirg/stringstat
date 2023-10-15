package dev.lampirg.logic.entities.line;

public class Pointer {

    private Line pointedLine;

    private Pointer(Line group) {
        this.pointedLine = group;
    }

    public static Pointer toLine(Line group) {
        return new Pointer(group);
    }

    public static Pointer empty() {
        return new Pointer(null);
    }

    public Line getPointedLine() {
        return pointedLine;
    }

    public void setPointedLine(Line pointer) {
        this.pointedLine = pointer;
    }
}
