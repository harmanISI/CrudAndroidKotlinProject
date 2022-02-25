package com.example.androidkotlinproject.db

import android.content.ContentValues
import android.content.Context
import com.example.androidkotlinproject.entites.User

class UserTable(context: Context) {
    private val dbHelper = DbHelper(context)


    fun insertData(firstName: String, lastName: String, emailId: String,course:String):Long {
        //Map of column name + row value
        val values = ContentValues().apply {
            put(DBContract.UserTable.FIRST_NAME, firstName)
            put(DBContract.UserTable.LAST_NAME, lastName)
            put(DBContract.UserTable.EMAIL_ID, emailId)
            put(DBContract.UserTable.COURSE, course)
        }

        val writeToDb = dbHelper.writableDatabase //EXPENSIVE if DB is closed
        //Second argument: What to do when there is no value.
        // Because of null: If there is no value then we just do not insert.
        val newRowId = writeToDb.insert(DBContract.UserTable.TABLE_NAME, null, values)
        return newRowId

    }

    fun get(firstname: Int): User? {
        val readFromDb = dbHelper.readableDatabase //EXPENSIVE if DB is closed.

        //Select Columns you want
        val projection = arrayOf(
            DBContract.UserTable.USER_ID,
            DBContract.UserTable.FIRST_NAME,
            DBContract.UserTable.LAST_NAME,
            DBContract.UserTable.EMAIL_ID,
                    DBContract.UserTable.COURSE
        )

        val cursor = readFromDb.rawQuery("SELECT * from ${DBContract.UserTable.TABLE_NAME} " +
                "where ${DBContract.UserTable.USER_ID} = '$firstname'", null)

        with(cursor) {
            if (moveToNext()){
                val user = User(
                    getInt(getColumnIndexOrThrow(DBContract.UserTable.USER_ID)),
                    getString(getColumnIndexOrThrow(DBContract.UserTable.FIRST_NAME)),
                    getString(getColumnIndexOrThrow(DBContract.UserTable.LAST_NAME)),
                    getString(getColumnIndexOrThrow(DBContract.UserTable.EMAIL_ID)),
                    getString(getColumnIndexOrThrow(DBContract.UserTable.COURSE))
                    )
                return user
            }
            else return null
        }
    }

    fun getAll(): MutableList<User> {
        val readFromDb = dbHelper.readableDatabase //EXPENSIVE if DB is closed.

        //Select Columns you want
        val projection = arrayOf(
            DBContract.UserTable.USER_ID,
            DBContract.UserTable.FIRST_NAME,
            DBContract.UserTable.LAST_NAME,
            DBContract.UserTable.EMAIL_ID,
            DBContract.UserTable.COURSE
        )

        val cursor = readFromDb.query(
            DBContract.UserTable.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val userList = mutableListOf<User>()

        with(cursor) {
            while (moveToNext()) {//Moves from -1 row to next one
                val user = User(
                    getInt(getColumnIndexOrThrow(DBContract.UserTable.USER_ID)),
                    getString(getColumnIndexOrThrow(DBContract.UserTable.FIRST_NAME)),
                    getString(getColumnIndexOrThrow(DBContract.UserTable.LAST_NAME)),
                    getString(getColumnIndexOrThrow(DBContract.UserTable.EMAIL_ID)),
                    getString(getColumnIndexOrThrow(DBContract.UserTable.COURSE))
                )
                userList.add(user)
            }
        }
        cursor.close()
        return userList
    }

    fun update(user: User?): Boolean {
        val dbWrite = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DBContract.UserTable.FIRST_NAME, user?.firstName)
            put(DBContract.UserTable.LAST_NAME, user?.lastName)
            put(DBContract.UserTable.EMAIL_ID, user?.email)
        }

        val whereClaus = "${DBContract.UserTable.USER_ID} = ?"
        val whereClausArgs = arrayOf(user?.id.toString())

        val rowsUpdated = dbWrite.update(
            DBContract.UserTable.TABLE_NAME,
            values,
            whereClaus,
            whereClausArgs
        )

        if (rowsUpdated == 1)
            return true
        return false
    }

    fun delete(user: User?) : Boolean {

        val dbWrite = dbHelper.writableDatabase

        val whereClaus = "${DBContract.UserTable.USER_ID} LIKE ?"
        val whereClausArgs = arrayOf(user?.id.toString())

        val deletedRows = dbWrite.delete(
            DBContract.UserTable.TABLE_NAME,
            whereClaus,
            whereClausArgs
        )

        return deletedRows > 0
    }
}