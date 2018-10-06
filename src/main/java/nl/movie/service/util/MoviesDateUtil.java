package nl.movie.service.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class MoviesDateUtil {

    public static final String YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String HH_MM = "HH:mm";
    public static final String DD_MM_YYYY = "dd-MM-yyyy";


    private MoviesDateUtil() {
    }

    public static String extractDate(final String dateTimeString) {
        try {
            final Date date = new SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS).parse(dateTimeString);
            return new SimpleDateFormat(DD_MM_YYYY).format(date);
        } catch (final Exception e) {
            return "";
        }
    }

    public static String extractTime(final String dateTimeString) {
        try {
            final Date date = new SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS).parse(dateTimeString);
            return new SimpleDateFormat(HH_MM).format(date);
        } catch (final Exception e) {
            return "";
        }
    }

    public static boolean sameDay(final Date date1, final String date2AsString) {

        boolean result = false;

        try {
            result = DateUtils.isSameDay(date1, new SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS).parse(date2AsString));
        } catch (final Exception e) {
        }

        return result;
    }

}