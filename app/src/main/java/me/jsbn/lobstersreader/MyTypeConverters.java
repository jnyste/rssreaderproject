package me.jsbn.lobstersreader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.room.TypeConverter;

/**
 * Helper method for Room to convert List of Strings to a comma concatenated String for database storage.
 */
public class MyTypeConverters {
    @TypeConverter
    /**
     * Convert a comma concatenated string ("data,linux,privacy") to a List of strings ("data", "linux", "privacy")
     */
    public static ArrayList<String> stringToStrings(String data) {
        ArrayList<String> categories = new ArrayList<>(Arrays.asList(data.split(",")));
        return categories;
    }
    /**
     * Convert a List of strings ("data", "linux", "privacy")  to a comma concatenated string ("data,linux,privacy")
     */
    @TypeConverter
    public static String stringsToString(ArrayList<String> data) {
        return String.join(",", data);
    }
}
