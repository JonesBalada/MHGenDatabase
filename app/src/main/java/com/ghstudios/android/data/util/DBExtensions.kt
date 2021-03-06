package com.ghstudios.android.data.util

import android.database.Cursor

fun Cursor.hasColumn(columnName: String): Boolean {
    return getColumnIndex(columnName) != -1
}

/**
 * Returns true if the value in the column is null, using the column name.
 */
fun Cursor.isNull(columnName: String): Boolean {
    return this.isNull(getColumnIndex(columnName))
}

fun Cursor.getLong(columnName: String): Long {
    return this.getLong(getColumnIndex(columnName))
}

/**
 * Retrieves the value of the requested column as a string, using the column name.
 */
fun Cursor.getString(columnName: String) : String? {
    return this.getString(getColumnIndex(columnName))
}


/**
 * Retrieves the value of the requested column as a string, using the column name.
 * Returns the default value if null
 */
fun Cursor.getString(columnName: String, default: String = "") : String {
    return this.getString(columnName) ?: default
}

/**
 * Retrieves the value of the requested column as a string, using the column name.
 */
fun Cursor.getInt(columnName: String) : Int {
    return this.getInt(getColumnIndex(columnName))
}

/**
 * Retrieves the value of the requested column as an integer evaluated as a boolean.
 * All non-zero values evaluate to true. Zero is false
 */
fun Cursor.getBoolean(columnName : String) : Boolean {
    return this.getInt(columnName) != 0
}