package me.jsbn.lobstersreader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.room.TypeConverter;

public class MyTypeConverters {
    @TypeConverter
    public static ArrayList<String> stringToStrings(String data) {
        ArrayList<String> categories = new ArrayList<>(Arrays.asList(data.split(",")));
        return categories;
    }

    @TypeConverter
    public static String stringsToString(ArrayList<String> data) {
        return String.join(",", data);
    }
}
