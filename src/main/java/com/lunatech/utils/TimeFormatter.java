package com.lunatech.utils;

import com.lunatech.models.TimeEntry;
import io.quarkus.qute.TemplateExtension;

import java.util.concurrent.TimeUnit;

public class TimeFormatter {

    @TemplateExtension
    static String durationAsText(TimeEntry timeEntry) {
        Long asNanos = timeEntry.duration.toMillis();
        String res = "";
        long days = TimeUnit.MILLISECONDS.toDays(asNanos);
        long hours = TimeUnit.MILLISECONDS.toHours(asNanos)
                - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(asNanos));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(asNanos)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(asNanos));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(asNanos)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(asNanos));
        if (days == 0) {
            res = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            res = String.format("%dd%02d:%02d:%02d", days, hours, minutes, seconds);
        }
        return res;
    }
}
