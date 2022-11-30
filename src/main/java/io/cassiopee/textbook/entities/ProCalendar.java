package io.cassiopee.textbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.cassiopee.textbook.entities.enums.TypeDay;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProCalendar extends DateAudit{
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type="uuid-char")
    private UUID id;
    private TypeDay typeDay;
    private Date startedHours;
    private Date endedHours;
    private double duration;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private SubjectField subjectField;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private AcademicYear academicYear;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProgrammeCourse programmeCourse;

    public ProCalendar() {
    }

    public ProCalendar(TypeDay typeDay, Date startedHours, Date endedHours, double duration, SubjectField subjectField, AcademicYear academicYear) {
        this.typeDay = typeDay;
        this.startedHours = startedHours;
        this.endedHours = endedHours;
        this.duration = duration;
        this.subjectField = subjectField;
        this.academicYear = academicYear;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TypeDay getTypeDay() {
        return typeDay;
    }

    public void setTypeDay(TypeDay typeDay) {
        this.typeDay = typeDay;
    }

    public Date getStartedHours() {
        return startedHours;
    }

    public void setStartedHours(Date startedHours) {
        this.startedHours = startedHours;
    }

    public Date getEndedHours() {
        return endedHours;
    }

    public void setEndedHours(Date endedHours) {
        this.endedHours = endedHours;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public SubjectField getSubjectField() {
        return subjectField;
    }

    public void setSubjectField(SubjectField subjectField) {
        this.subjectField = subjectField;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public ProgrammeCourse getProgrammeCourse() {
        return programmeCourse;
    }

    public void setProgrammeCourse(ProgrammeCourse programmeCourse) {
        this.programmeCourse = programmeCourse;
    }

    @Override
    public String toString() {
        return "ProCalendar{" +
                "id=" + id +
                ", typeDay=" + typeDay +
                ", startedHours=" + startedHours +
                ", endedHours=" + endedHours +
                ", duration=" + duration +
                ", subjectField=" + subjectField +
                ", academicYear=" + academicYear +
                ", programmeCourse=" + programmeCourse +
                '}';
    }
}
