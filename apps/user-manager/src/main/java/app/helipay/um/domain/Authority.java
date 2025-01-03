package app.helipay.um.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;

@Setter
@Getter
@Entity
@Table(name = "um_authority")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = {"new", "id"})
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Authority implements Serializable, Persistable<String> {

    @Serial
    private static final long serialVersionUID = 1L;


    @NotNull
    @Size(max = 50)
    @Id
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Transient
    private boolean isPersisted;

    @Column(name = "created")
    @CreationTimestamp
    private Instant created;
    @Column(name = "changed")
    @UpdateTimestamp
    private Instant changed;


    public Authority(String name) {
        this.name = name;
    }

    public Authority name(String name) {
        this.setName(name);
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @Override
    public String getId() {
        return this.name;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Authority setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Authority)) {
            return false;
        }
        return getName() != null && getName().equals(((Authority) o).getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Authority{" +
                "name=" + getName() +
                "}";
    }
}