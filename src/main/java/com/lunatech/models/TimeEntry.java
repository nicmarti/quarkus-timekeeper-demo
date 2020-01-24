package com.lunatech.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class TimeEntry extends PanacheEntity {
    public String entryId;

    @NotNull
    @Size(max=500)
    public String description;

    public String author;

    public TimeEntry() {
    }
}
