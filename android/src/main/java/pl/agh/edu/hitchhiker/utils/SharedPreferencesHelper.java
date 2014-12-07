package pl.agh.edu.hitchhiker.utils;

import android.content.Context;
import android.content.SharedPreferences;

import pl.agh.edu.hitchhiker.HitchhikerApp;

public class SharedPreferencesHelper {
    public static final String PREFS_NAME = "HitchhikerPreferences";


    public static int getInt(String key, int defValue) {
        SharedPreferences settings = HitchhikerApp.getContext().getSharedPreferences(PREFS_NAME, 0);
        int toReturn = settings.getInt(key, defValue);
        return toReturn;
    }

    public static boolean setInt(String key, int value) {
        SharedPreferences settings = HitchhikerApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static String getString(String key, String defValue) {
        SharedPreferences settings = HitchhikerApp.getContext().getSharedPreferences(PREFS_NAME, 0);
        String toReturn = settings.getString(key, defValue);
        return toReturn;
    }

    public static boolean setString(String key, String value) {
        SharedPreferences settings = HitchhikerApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }
}
