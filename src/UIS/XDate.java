package UIS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XDate {
    static SimpleDateFormat formater = new SimpleDateFormat();
    
    public static Date toDate(String date, String pattern) {
        try {
            formater.applyPattern(pattern);
            return formater.parse(date);
        } 
        catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static String toString(Date date, String pattern) {
        formater.applyPattern(pattern);
        return formater.format(date);
    }

    
    public static Date addDays(Date date, long days) {
        date.setTime(date.getTime() + days*24*60*60*1000);
        return date;
    }
    
    public static String formatDate(String date) {
        String ar[] = date.split(" ")[0].split("-");
        date = ar[2] + "-" + ar[1] + "-" + ar[0];
        return date;
    }
}
