package database;

import androidx.room.TypeConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Converters {
    static DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @TypeConverter
    public static LocalDate fromDateString(String string){
        return LocalDate.parse(string, formatter);
    }

    @TypeConverter
    public static String fromLocalDate(LocalDate date){
        return formatter.format(date);
    }
}
