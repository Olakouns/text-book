package io.cassiopee.textbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.cassiopee.textbook.entities.enums.Years;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "classes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClassRoom extends DateAudit{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Years years;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Field field;
    @OneToMany
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Actor> classRepresentative;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AcademicYear academicYear;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Option option;

    public ClassRoom() {
    }

    public ClassRoom(String name, Years years, Field field, List<Actor> classRepresentative, AcademicYear academicYear, Option option) {
        this.name = name;
        this.years = years;
        this.field = field;
        this.classRepresentative = classRepresentative;
        this.academicYear = academicYear;
        this.option = option;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Years getYears() {
        return years;
    }

    public void setYears(Years years) {
        this.years = years;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public List<Actor> getClassRepresentative() {
        return classRepresentative;
    }

    public void setClassRepresentative(List<Actor> classRepresentative) {
        this.classRepresentative = classRepresentative;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "ClassRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", years=" + years +
                ", field=" + field +
                ", classRepresentative=" + classRepresentative +
                ", academicYear=" + academicYear +
                ", option=" + option +
                '}';
    }
}
