package org.tianjyan.luban.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {
    private static SimpleDateFormat simpleTimeFormat =
            new SimpleDateFormat("HH:mm:ss.SSS", Locale.US);

    private static SimpleDateFormat simpleDateTimeFormat =
            new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US);

    public static String getSystemTime(long date) {
        return simpleTimeFormat.format(new Date(date));
    }

    public static String getDateTimeString() {
        return simpleDateTimeFormat.format(new Date());
    }
}
