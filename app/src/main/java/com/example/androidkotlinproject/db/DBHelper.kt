package com.example.androidkotlinproject.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val SQL_CREATE_TABLE =
    "CREATE TABLE ${DBContract.UserTable.TABLE_NAME} (" +
            "${DBContract.UserTable.USER_ID} INTEGER PRIMARY KEY, " + //"${BaseColumns._ID}"
            "${DBContract.UserTable.FIRST_NAME} TEXT, " +
            "${DBContract.UserTable.LAST_NAME} TEXT," +
            "${DBContract.UserTable.EMAIL_ID} TEXT," +
            "${DBContract.UserTable.COURSE} TEXT" +

            ")"

private const val DROP_TABLE = "DROP TABLE IF EXISTS ${DBContract.UserTable.TABLE_NAME}"

class DbHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        const val DATABASE_NAME = "db"
        const val DATABASE_VERSION = 11
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}

