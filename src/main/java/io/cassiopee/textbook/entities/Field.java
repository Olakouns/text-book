package io.cassiopee.textbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.cassiopee.textbook.entities.enums.Years;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "filieres")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Field extends DateAudit{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String name;
    private String code;
    @OneToMany(mappedBy = "field")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Option> options;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private Actor head;

    public Field() {
    }

    public Field(String name, String code, List<Option> options) {
        this.name = name;
        this.code = code;
        this.options = options;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Actor getHead() {
        return head;
    }

    public void setHead(Actor head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "Field{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", options=" + options +
                ", head=" + head +
                '}';
    }
}
