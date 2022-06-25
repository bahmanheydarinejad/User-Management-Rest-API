package ir.usermanagement.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class AppBaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    protected Long id;

    @Column(name = "DATE_CREATE")
    protected Long createTimeStamp;

    @Column(name = "DATE_MODIFIED")
    protected Long updateTimeStamp;

    @PrePersist
    private void prePersist() {
        createTimeStamp = System.currentTimeMillis();
    }

    @PreUpdate
    private void preUpdate() {
        updateTimeStamp = System.currentTimeMillis();
    }

}
