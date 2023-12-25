package com.hcmus.ui.table;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class UnixTimestampConverter {
    public static LocalDateTime unix2DateTime(long unixVal) {
        Instant instant = Instant.ofEpochSecond(unixVal);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static long dateTime2Unix(LocalDateTime dateTimeVal) {
        Instant instant = dateTimeVal.atZone(ZoneId.systemDefault()).toInstant();
        return instant.getEpochSecond();
    }
}
