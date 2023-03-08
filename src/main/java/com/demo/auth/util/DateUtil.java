package com.demo.auth.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Component;

@Log4j2
@Component
public class DateUtil {
    public static Timestamp getCurrentDate() {
        Timestamp today = null;
        try {
            Date nowDate = Calendar.getInstance().getTime();
            today = new Timestamp(nowDate.getTime());
        } catch (Exception e) {
            log.error("error msg : {} ", e);
            throw new RuntimeException(e);
        }
        return today;
    }
}
