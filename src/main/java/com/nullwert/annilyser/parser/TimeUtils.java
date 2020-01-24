package com.nullwert.annilyser.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    private static Logger logger = LoggerFactory.getLogger(TimeUtils.class);

    public static long convertTimestampToMillis(String timeStamp) {
        final SimpleDateFormat simpleDateFormatHour = new SimpleDateFormat("dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormatHour.parse(timeStamp);
            return date.getTime();
        } catch (ParseException e) {
            logger.error("", e);
            return 0;
        }
    }
}
