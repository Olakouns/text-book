package io.cassiopee.textbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProgrammeCourse extends DateAudit{
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type="uuid-char")
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Actor professor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private SubjectField subjectField;
    private double programmeDuration;
    private Date startedDate;
    private Date endedDate;
    private boolean status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private AcademicYear academicYear;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private Field field;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ClassRoom classRoom;
    private boolean started;

    public ProgrammeCourse() {
    }

    public ProgrammeCourse(Actor professor, SubjectField subjectField, double programmeDuration, Date startedDate, Date endedDate, boolean status, AcademicYear academicYear) {
        this.professor = professor;
        this.subjectField = subjectField;
        this.programmeDuration = programmeDuration;
        this.startedDate = startedDate;
        this.endedDate = endedDate;
        this.status = status;
        this.academicYear = academicYear;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Actor getProfessor() {
        return professor;
    }

    public void setProfessor(Actor professor) {
        this.professor = professor;
    }

    public SubjectField getSubjectField() {
        return subjectField;
    }

    public void setSubjectField(SubjectField subjectField) {
        this.subjectField = subjectField;
    }

    public double getProgrammeDuration() {
        return programmeDuration;
    }

    public void setProgrammeDuration(double programmeDuration) {
        this.programmeDuration = programmeDuration;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
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

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    @Override
    public String toString() {
        return "ProgrammeCourse{" +
                "id=" + id +
                ", professor=" + professor +
                ", subjectField=" + subjectField +
                ", programmeDuration=" + programmeDuration +
                ", startedDate=" + startedDate +
                ", endedDate=" + endedDate +
                ", status=" + status +
                ", academicYear=" + academicYear +
                ", field=" + field +
                ", classRoom=" + classRoom +
                ", started=" + started +
                '}';
    }
}
