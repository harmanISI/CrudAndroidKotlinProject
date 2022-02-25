package com.example.androidkotlinproject.db

import android.provider.BaseColumns

object DBContract {
    //Tables
    object UserTable : BaseColumns {
        const val TABLE_NAME = "user"
        const val USER_ID = "user_id"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val EMAIL_ID = "email_id"
        const val COURSE = "course"
    }

}