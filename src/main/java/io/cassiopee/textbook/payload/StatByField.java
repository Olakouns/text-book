package io.cassiopee.textbook.payload;

import io.cassiopee.textbook.entities.Actor;
import io.cassiopee.textbook.entities.Field;

public class StatByField {
    private Actor head;
    private Field field;
    private long nbrOption;
    private long nbrClass;
    private long nbrClassRoomFirst;
    private long nbrClassRoomSecond;
    private long nbrClassRooThird;

    public StatByField() {
    }

    public StatByField(Actor head, Field field, long nbrOption, long nbrClass, long nbrClassRoomFirst, long nbrClassRoomSecond, long nbrClassRooThird) {
        this.head = head;
        this.field = field;
        this.nbrOption = nbrOption;
        this.nbrClass = nbrClass;
        this.nbrClassRoomFirst = nbrClassRoomFirst;
        this.nbrClassRoomSecond = nbrClassRoomSecond;
        this.nbrClassRooThird = nbrClassRooThird;
    }

    public Actor getHead() {
        return head;
    }

    public void setHead(Actor head) {
        this.head = head;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public long getNbrOption() {
        return nbrOption;
    }

    public void setNbrOption(long nbrOption) {
        this.nbrOption = nbrOption;
    }

    public long getNbrClass() {
        return nbrClass;
    }

    public void setNbrClass(long nbrClass) {
        this.nbrClass = nbrClass;
    }

    public long getNbrClassRoomFirst() {
        return nbrClassRoomFirst;
    }

    public void setNbrClassRoomFirst(long nbrClassRoomFirst) {
        this.nbrClassRoomFirst = nbrClassRoomFirst;
    }

    public long getNbrClassRoomSecond() {
        return nbrClassRoomSecond;
    }

    public void setNbrClassRoomSecond(long nbrClassRoomSecond) {
        this.nbrClassRoomSecond = nbrClassRoomSecond;
    }

    public long getNbrClassRooThird() {
        return nbrClassRooThird;
    }

    public void setNbrClassRooThird(long nbrClassRooThird) {
        this.nbrClassRooThird = nbrClassRooThird;
    }

    @Override
    public String toString() {
        return "StatByField{" +
                "head=" + head +
                ", field=" + field +
                ", nbrOption=" + nbrOption +
                ", nbrClass=" + nbrClass +
                ", nbrClassRoomFirst=" + nbrClassRoomFirst +
                ", nbrClassRoomSecond=" + nbrClassRoomSecond +
                ", nbrClassRooThird=" + nbrClassRooThird +
                '}';
    }
}
