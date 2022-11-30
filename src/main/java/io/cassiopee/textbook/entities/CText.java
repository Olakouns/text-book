package io.cassiopee.textbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "cahiers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CText extends DateAudit{
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type="uuid-char")
    private UUID id;
    @ManyToOne
    @JoinColumn
    private ClassRoom classRoom;
    @ManyToOne
    @JoinColumn
    private AcademicYear academicYear;

    public CText() {
    }

    public CText(ClassRoom classRoom, AcademicYear academicYear) {
        this.classRoom = classRoom;
        this.academicYear = academicYear;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    @Override
    public String toString() {
        return "CText{" +
                "id=" + id +
                ", classRoom=" + classRoom +
                ", academicYear=" + academicYear +
                '}';
    }
}
