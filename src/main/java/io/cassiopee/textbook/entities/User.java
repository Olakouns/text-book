package io.cassiopee.textbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.cassiopee.textbook.entities.enums.StatusUser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends DateAudit{

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type="uuid-char")
    private UUID id;

    @Size(max = 40)
    private String name;

    @Size(max = 40)
    @Column(unique = true)
    private String username;

    @Size(max = 100, min = 6)
    @NotBlank
    @JsonIgnore
    private String password;

    @Email
    private String email;

    @JsonIgnore
    private boolean active;

    @Enumerated(EnumType.STRING)
    private StatusUser status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Role> roles;

    public User() {
        super();
    }

    public User(UUID id, String name, String username, String password, String email, boolean active, StatusUser status, Collection<Role> roles) {
        super();
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.active = active;
        this.status = status;
        this.roles = roles;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    @JsonIgnore
    public String getPassword() {
        return password;
    }

//    @JsonSetter
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    @JsonIgnore
    public Collection<Role> getRoles() {
        return roles;
    }

//    @JsonSetter
    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public StatusUser getStatus() {
        return status;
    }

    public void setStatus(StatusUser status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                ", status=" + status +
                ", roles=" + roles +
                '}';
    }
}
