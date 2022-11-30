package io.cassiopee.textbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.cassiopee.textbook.entities.enums.TypePermission;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "permissions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Permission extends DateAudit{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypePermission name;
    private String description;

    public Permission() {
    }

    public Permission(Long id, TypePermission name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypePermission getName() {
        return name;
    }

    public void setName(TypePermission name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name=" + name +
                ", description='" + description + '\'' +
                '}';
    }
}
