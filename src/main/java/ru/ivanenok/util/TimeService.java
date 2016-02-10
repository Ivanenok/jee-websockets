package ru.ivanenok.util;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Created by ivanenok on 2/11/16.
 */
public class TimeService {
    public static LocalDateTime now() {
        return LocalDateTime.now(Clock.systemUTC());
    }
}
