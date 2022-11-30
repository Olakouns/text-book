package io.cassiopee.textbook.payload;

import io.cassiopee.textbook.entities.ClassRoom;
import io.cassiopee.textbook.entities.ProCalendar;

import java.util.List;

public class TimesTables {
    private ClassRoom classRoom;
    private List<ProCalendar> proCalendars;

    public TimesTables(ClassRoom classRoom, List<ProCalendar> proCalendars) {
        this.classRoom = classRoom;
        this.proCalendars = proCalendars;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public List<ProCalendar> getProCalendars() {
        return proCalendars;
    }

    public void setProCalendars(List<ProCalendar> proCalendars) {
        this.proCalendars = proCalendars;
    }

    @Override
    public String toString() {
        return "TimesTables{" +
                "classRoom=" + classRoom +
                ", proCalendars=" + proCalendars +
                '}';
    }
}
