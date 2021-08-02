package by.issoft.test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Util {
    public static LocalDate getLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
