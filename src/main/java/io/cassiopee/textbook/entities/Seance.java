package io.cassiopee.textbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "seances")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Seance extends DateAudit{
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type="uuid-char")
    private UUID id;
    @ManyToOne
    @JoinColumn
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CText cText;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private SubjectField subjectField;
    private String workDone;
    private Date startedDate;
    private Date endedDate;
    @ManyToOne
    @JoinColumn
    private Actor supervisor;
    private boolean validate;
    private double duration;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Actor headClass;
    private Date day;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProgrammeCourse programmeCourse;

    public Seance() {
    }

    public Seance(CText cText, SubjectField subjectField, String workDone,
                  Date startedDate, Date endedDate, Actor supervisor, boolean validate, double duration, Actor headClass, Date day) {
        this.cText = cText;
        this.subjectField = subjectField;
        this.workDone = workDone;
        this.startedDate = startedDate;
        this.endedDate = endedDate;
        this.supervisor = supervisor;
        this.validate = validate;
        this.duration = duration;
        this.headClass = headClass;
        this.day = day;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CText getcText() {
        return cText;
    }

    public void setcText(CText cText) {
        this.cText = cText;
    }

    public SubjectField getSubjectField() {
        return subjectField;
    }

    public void setSubjectField(SubjectField subjectField) {
        this.subjectField = subjectField;
    }

    public String getWorkDone() {
        return workDone;
    }

    public void setWorkDone(String workDone) {
        this.workDone = workDone;
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

    public Actor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Actor supervisor) {
        this.supervisor = supervisor;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public Actor getHeadClass() {
        return headClass;
    }

    public void setHeadClass(Actor headClass) {
        this.headClass = headClass;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public ProgrammeCourse getProgrammeCourse() {
        return programmeCourse;
    }

    public void setProgrammeCourse(ProgrammeCourse programmeCourse) {
        this.programmeCourse = programmeCourse;
    }

    @Override
    public String toString() {
        return "Seance{" +
                "id=" + id +
                ", cText=" + cText +
                ", subjectField=" + subjectField +
                ", workDone='" + workDone + '\'' +
                ", startedDate=" + startedDate +
                ", endedDate=" + endedDate +
                ", supervisor=" + supervisor +
                ", validate=" + validate +
                ", duration=" + duration +
                ", headClass=" + headClass +
                ", day=" + day +
                '}';
    }
}
