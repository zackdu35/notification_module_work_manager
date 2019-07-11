package fr.dreamo.notification

import android.content.Context
import android.preference.PreferenceManager


class PrefUtils {

    companion object {

        /**
         * Called to save supplied value in shared preferences against given key.
         * @param context Context of caller activity
         * @param key Key of value to save against
         * @param value Value to save
         */
        fun saveToPrefs(context: Context, key: String, value: String) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()
            editor.putString(key, value).apply()
        }

        fun saveToPrefs(context: Context, key: String, value: Boolean) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()
            editor.putBoolean(key, value).apply()
        }

        fun saveToPrefs(context: Context, key: String, value: Long) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()
            editor.putLong(key, value).apply()
        }

        fun saveToPrefs(context: Context, key: String, value: Int) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()
            editor.putInt(key, value).apply()
        }

        /**
         * Called to retrieve required value from shared preferences, identified by given key.
         * Default value will be returned of no value found or error occurred.
         * @param context Context of caller activity
         * @param key Key to find value against
         * @param defaultValue Value to return if no data found against given key
         * @return Return the value found against given key, default if not found or any error occurs
         */
        fun getFromPrefs(context: Context, key: String, defaultValue: String): String? {
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            try {
                return sharedPrefs.getString(key, defaultValue)
            } catch (e: Exception) {
                e.printStackTrace()
                return defaultValue
            }
        }

        fun getFromPrefs(context: Context, key: String, defaultValue: Boolean): Boolean {
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            try {
                return sharedPrefs.getBoolean(key, defaultValue)
            } catch (e: Exception) {
                e.printStackTrace()
                return defaultValue
            }
        }

        fun getFromPrefs(context: Context, key: String, defaultValue: Long): Long {
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            try {
                return sharedPrefs.getLong(key, defaultValue)
            } catch (e: Exception) {
                e.printStackTrace()
                return defaultValue
            }
        }

        fun getFromPrefs(context: Context, key: String, defaultValue: Int): Int {
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            try {
                return sharedPrefs.getInt(key, defaultValue)
            } catch (e: Exception) {
                e.printStackTrace()
                return defaultValue
            }
        }

        /**
         *
         * @param context Context of caller activity
         * @param key Key to delete from SharedPreferences
         */
        fun removeFromPrefs(context: Context, key: String) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()
            editor.remove(key).apply()
        }
    }
}