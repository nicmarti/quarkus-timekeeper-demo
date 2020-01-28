package com.lunatech.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;

@Entity
@RegisterForReflection
public class TimeEntry extends PanacheEntity {
    public String entryId;

    @NotNull
    @Size(max = 500)
    public String description;

    public String author;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    public Date created;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    public Date updated;

    public Duration duration ;

    public LocalDate entryDate;

    public TimeEntry() {
    }

    public String durationAsString(){
        if(this.duration==null){
            return "-";
        }else{
            long s = duration.getSeconds();
            if(s == 0){
                return "0";
            }else {
                return String.format("%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
            }
        }

    }
}
