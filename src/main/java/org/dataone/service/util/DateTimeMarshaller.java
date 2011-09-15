/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dataone.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 *
 * @author waltz
 */
public class DateTimeMarshaller {

    static final Pattern timezonePattern = Pattern.compile(".+(?:(?:[\\+\\-]\\d\\d:?\\d\\d)|Z)$");

    static final DateTimeFormatter basicDateIsoFmt = ISODateTimeFormat.basicDate();
    static final DateTimeFormatter basicDateTimeIsoFmt = ISODateTimeFormat.basicDateTime();
    static final DateTimeFormatter basicDateTimeNoMillisIsoFmt = ISODateTimeFormat.basicDateTimeNoMillis();
    static final DateTimeFormatter extendedDateIsoFmt = ISODateTimeFormat.date();
    static final DateTimeFormatter extendedDateTimeIsoFmt = ISODateTimeFormat.dateTime();
    static final DateTimeFormatter extendedDateTimeNoMillisIsoFmt = ISODateTimeFormat.dateTimeNoMillis();
    static final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
//    static final DateTimeFormatter zFmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    /**
     * convert a date to GMT
     *
     * @param d
     * @return dateTime string that is ISO 8601 compliant
     */
    public static String serializeDateToUTC(Date d) {
        DateTime dt = new DateTime(d);
        DateTime dtUTC = dt.withZone(DateTimeZone.UTC);
        return fmt.print(dtUTC);

    }

    /**
     * convert a String to GMT date
     * The STring must either be ISO 8601 compliant or
     * http://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.3.1 compliant
     * @param d
     * @return date in UTC
     */
    public static Date deserializeDateToUTC(String dt) {
        // if the string can not be parsed, then a Null Pointer Exception will be thrown
        DateTime dateTime = null;


        if (Character.isDigit(dt.charAt(0))) {
            // Assume it is ISO 8601 compliant
            // either simple or extended date
            String tzAppend = "";
            if (!(timezonePattern.matcher(dt).matches())) {
                tzAppend = "Z";
            }
            if (Character.isDigit(dt.charAt(4))) {
               //basic
                if (dt.contains(".")) {
                    dateTime = basicDateTimeIsoFmt.parseDateTime(dt+tzAppend);
                } else {
                    dateTime = basicDateTimeNoMillisIsoFmt.parseDateTime(dt+tzAppend);
                }
            } else {
                // extended
                if (dt.contains(".")) {
                    dateTime = extendedDateTimeIsoFmt.parseDateTime(dt+tzAppend);

                } else {
                    dateTime = extendedDateTimeNoMillisIsoFmt.parseDateTime(dt+tzAppend);
                }
            }
        } else {
            if (Character.isSpaceChar(dt.charAt(3))) {
                // it better be a string that looks something like
                // Sun Nov  6 08:49:37 1994       ; ANSI C's asctime() format
                SimpleDateFormat format = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy");
                try {
                    dateTime = new DateTime(format.parse(dt));
                } catch (ParseException ex) {
                    dateTime = null;
                }
            } else if (Character.isLetter(dt.charAt(3))) {
                // it better be a string that looks something like
                // Sunday, 06-Nov-94 08:49:37 GMT ; RFC 850, obsoleted by RFC 1036
                SimpleDateFormat format = new SimpleDateFormat("EEEE, dd-MMM-yy HH:mm:ss zzz");
                try {
                    dateTime = new DateTime(format.parse(dt));
                } catch (ParseException ex) {
                    dateTime = null;
                }
            } else {
                // it better be a string that looks something like
                // Sun, 06 Nov 1994 08:49:37 GMT  ; RFC 822, updated by RFC 1123
                SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
                try {
                    dateTime = new DateTime(format.parse(dt));
                } catch (ParseException ex) {
                    dateTime = null;
                }
            }
        }
        dateTime = dateTime.withZone(DateTimeZone.UTC);
        return dateTime.toDate();
    }
}
