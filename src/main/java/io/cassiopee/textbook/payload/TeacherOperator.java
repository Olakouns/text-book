package io.cassiopee.textbook.payload;

import io.cassiopee.textbook.entities.ClassRoom;
import io.cassiopee.textbook.entities.Field;
import io.cassiopee.textbook.entities.SubjectField;

import java.util.Date;
import java.util.UUID;

public class TeacherOperator {
    private UUID programmeCourseId;
    private SubjectField subjectField;
    private Date startedDate;
    private Date endedDate;
    private Field field;
    private ClassRoom classRoom;

    public TeacherOperator() {
    }

    public TeacherOperator(UUID programmeCourseId, SubjectField subjectField, Date startedDate, Date endedDate, Field field, ClassRoom classRoom) {
        this.programmeCourseId = programmeCourseId;
        this.subjectField = subjectField;
        this.startedDate = startedDate;
        this.endedDate = endedDate;
        this.field = field;
        this.classRoom = classRoom;
    }

    public UUID getProgrammeCourseId() {
        return programmeCourseId;
    }

    public void setProgrammeCourseId(UUID programmeCourseId) {
        this.programmeCourseId = programmeCourseId;
    }

    public SubjectField getSubjectField() {
        return subjectField;
    }

    public void setSubjectField(SubjectField subjectField) {
        this.subjectField = subjectField;
    }

    public Date getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

    public Date getEndedDate() {
        return endedDate;
    }

    public void setEndedDate(Date endedDate) {
        this.endedDate = endedDate;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    @Override
    public String toString() {
        return "TeacherOperator{" +
                "programmeCourseId=" + programmeCourseId +
                ", subjectField=" + subjectField +
                ", startedDate=" + startedDate +
                ", endedDate=" + endedDate +
                ", field=" + field +
                ", classRoom=" + classRoom +
                '}';
    }
}
