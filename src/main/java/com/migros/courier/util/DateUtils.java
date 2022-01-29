package com.migros.courier.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    public static boolean lessThanOneMinute(LocalDateTime t1, LocalDateTime t2){
        return ChronoUnit.MINUTES.between(t1, t2) < 1;
    }
}
