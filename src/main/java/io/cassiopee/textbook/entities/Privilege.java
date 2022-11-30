package io.cassiopee.textbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.cassiopee.textbook.entities.enums.TypePrivilege;
import io.cassiopee.textbook.entities.enums.TypeRole;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "privileges")
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Privilege extends DateAudit{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypePrivilege name;
    private String description;
    @Enumerated(EnumType.STRING)
    private TypeRole typeRole;

    public Privilege(TypePrivilege name) {
        super();
        this.name = name;
    }

    public Privilege(TypePrivilege name, String description, TypeRole typeRole) {
        this.name = name;
        this.description = description;
        this.typeRole = typeRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypePrivilege getName() {
        return name;
    }

    public void setName(TypePrivilege name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeRole getTypeRole() {
        return typeRole;
    }

    public void setTypeRole(TypeRole typeRole) {
        this.typeRole = typeRole;
    }

    @Override
    public String toString() {
        return "Privilege{" +
                "id=" + id +
                ", name=" + name +
                ", description='" + description + '\'' +
                ", typeRole=" + typeRole +
                '}';
    }
}
