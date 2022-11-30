package io.cassiopee.textbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AcademicYear {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type="uuid-char")
    private UUID id;

    private String name;
    private Date startedDate;
    private Date endedDate;
    private boolean current;

    public AcademicYear(String name, Date startedDate, Date endedDate, boolean current) {
        this.name = name;
        this.startedDate = startedDate;
        this.endedDate = endedDate;
        this.current = current;
    }

    public AcademicYear() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "AcademicYear{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startedDate=" + startedDate +
                ", endedDate=" + endedDate +
                ", current=" + current +
                '}';
    }
}
