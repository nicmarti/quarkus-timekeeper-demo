package com.lunatech.services;
import com.lunatech.models.TimeEntry;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@ApplicationScoped
@Transactional(REQUIRED)
public class TimeEntryService {
      @Transactional(SUPPORTS)
    public List<TimeEntry> findAllTimeEntries() {
        return TimeEntry.listAll();
    }

    @Transactional(SUPPORTS)
    public TimeEntry findTimeEntryById(Long id) {
        return TimeEntry.findById(id);
    }

    public TimeEntry persist(@Valid TimeEntry timeEntry) {
          timeEntry.persist();
        return timeEntry;
    }

    public TimeEntry update(@Valid TimeEntry timeEntry) {
        TimeEntry entity = TimeEntry.findById(timeEntry.entryId);
        entity.description = timeEntry.description;
        entity.author = timeEntry.author;
        entity.duration = timeEntry.duration;
        return entity;
    }

    public void delete(Long id) {
        TimeEntry timeEntry = TimeEntry.findById(id);
        if(timeEntry!=null) {
            timeEntry.delete();
        }
    }
}
