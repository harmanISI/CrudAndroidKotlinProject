package com.example.androidkotlinproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SearchData : AppCompatActivity() {
    companion object {
        const val EXTRA_NAME_KEY = "receiverInfo"
    }
    val intentToPassBackwards : Intent = Intent()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_data)

        val userId = findViewById<TextView>(R.id.search_data_id)
        val firstname = findViewById<TextView>(R.id.search_data_firstname)
        val lastname = findViewById<TextView>(R.id.search_data_lastname)
        val email = findViewById<TextView>(R.id.search_data_email)
        val courseName = findViewById<TextView>(R.id.search_data_course)

        val myIntent = intent
        val bundle: Bundle? = intent.extras

        val tempEmail = bundle?.get("email")
        val tempID = bundle?.get("id")
        val tempLastName = bundle?.get("lastName")
        val tempFirstName = bundle?.get("firstname")
        val tempCourseName = bundle?.get("course")
        email.text=(intent.getStringExtra(ViewActivity.EXTRA_DATA_KEY))

        if (myIntent.hasExtra(ViewActivity.EXTRA_DATA_KEY))
        {
            userId.text= tempID.toString()
            firstname.text= tempFirstName.toString()
            lastname.text= tempLastName.toString()
            email.text= tempEmail.toString()
            courseName.text =tempCourseName.toString()
        }
    }
}