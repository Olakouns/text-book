package io.cassiopee.textbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.cassiopee.textbook.entities.enums.Semester;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "matieresdefiliere")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SubjectField extends DateAudit{
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type="uuid-char")
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Subject subject;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private AcademicYear academicYear;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Field field;
    @Enumerated(EnumType.STRING)
    private Semester semester;

    public SubjectField() {
    }

    public SubjectField(Subject subject, AcademicYear academicYear, Field field, Semester semester) {
        this.subject = subject;
        this.academicYear = academicYear;
        this.field = field;
        this.semester = semester;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "SubjectField{" +
                "id=" + id +
                ", subject=" + subject +
                ", academicYear=" + academicYear +
                ", field=" + field +
                ", semester=" + semester +
                '}';
    }
}
