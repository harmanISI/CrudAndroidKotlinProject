package com.example.androidkotlinproject

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AlertDialog
import com.example.androidkotlinproject.db.UserTable

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var firstName : EditText
    lateinit var lastName : EditText
    lateinit var emailId : EditText
    lateinit var saveButton : Button
    lateinit var viewButton : Button
    lateinit var userTable: UserTable
    lateinit var course: Spinner
    lateinit var  selectedCourse: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userTable = UserTable(this)
        firstName = findViewById(R.id.main_firstName)
        lastName = findViewById(R.id.main_lastName)
        emailId = findViewById(R.id.main_emailId)
        saveButton = findViewById(R.id.main_saveButton)
        viewButton = findViewById(R.id.main_viewButton)
        course = findViewById(R.id.main_course)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.course,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        course.adapter = adapter
        course.onItemSelectedListener = this

        saveButton.setOnClickListener {
            if(firstName.text.toString() == "" || lastName.text.toString() == "" || emailId.text.toString() == "")
            {
                Toast.makeText(this, "Any field should not be blank.!", LENGTH_SHORT).show()
            }
            else
            {
             userTable.insertData(firstName.text.toString(),lastName.text.toString(),emailId.text.toString(),selectedCourse)

                firstName.setText("")
                lastName.setText("")
                emailId.setText("")

                val builder = AlertDialog.Builder(this)
                //set title for alert dialog
                builder.setTitle("User SignUp")
                //set message for alert dialog
                builder.setMessage("Student Record is Inserted")
                builder.setIcon(android.R.drawable.ic_dialog_alert)

                //performing positive action
                builder.setPositiveButton("Yes"){dialogInterface, which ->
                    Toast.makeText(applicationContext,"Data Inserted ",Toast.LENGTH_LONG).show()
                }
                val alertDialog: AlertDialog = builder.create()

                alertDialog.show()

                val mp: MediaPlayer = MediaPlayer.create(
                    this,
                    R.raw.app_src_main_res_raw_cat_sound
                )
               mp.start()
            mp.setOnCompletionListener { mp -> mp.release()
            }
            }
        }

        viewButton.setOnClickListener{
            val intent = Intent(this, ViewActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        selectedCourse = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        selectedCourse=""
    }
}
