package io.cassiopee.textbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.cassiopee.textbook.entities.enums.TypeToken;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class ActivatorToken extends DateAudit {

    private static final long serialVersionUID = 6882094795903437346L;
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true)
    @Type(type="uuid-char")
    @JsonIgnore
    private UUID id;
    private String ref;
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private TypeToken type;
    private String token;
    @Basic
    private Instant expiration;

    public ActivatorToken() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public TypeToken getType() {
        return type;
    }

    public void setType(TypeToken type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }
}
