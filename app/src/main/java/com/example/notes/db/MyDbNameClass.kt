package com.example.notes.db

object MyDbNameClass {
    const val TABLE_NAME = "my_table"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_ID = "_id"
    const val COLUMN_NAME_CONTENT = "content"
    const val COLUMN_NAME_SETTIME = "settime"
    const val COLUMN_NAME_BOOL = "bool"
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "Notess.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME_TITLE TEXT,$COLUMN_NAME_CONTENT TEXT,$COLUMN_NAME_SETTIME TEXT,$COLUMN_NAME_BOOL TEXT)"
    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}