package dev.lampirg;

public class GroupPointer {

    private Group pointer;

    private GroupPointer(Group group) {
        this.pointer = group;
    }

    public static GroupPointer fromGroup(Group group) {
        return new GroupPointer(group);
    }

    public Group getGroup() {
        return pointer;
    }

    public void setPointer(Group pointer) {
        this.pointer = pointer;
    }
}
