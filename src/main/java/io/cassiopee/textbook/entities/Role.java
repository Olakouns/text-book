package io.cassiopee.textbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.cassiopee.textbook.entities.enums.TypeRole;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Entity
@Table(name = "roles")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role {
    private static final long serialVersionUID = 5611760913680161176L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private TypeRole type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Collection<Privilege> privileges;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private User createdBy;

    public Role(String name, String description, TypeRole type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public Role() {
    }

    public Role(String name, TypeRole type) {
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    @JsonSetter
    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public TypeRole getType() {
        return type;
    }

    public void setType(TypeRole type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", privileges=" + privileges +
                ", createdBy=" + createdBy +
                '}';
    }
}
