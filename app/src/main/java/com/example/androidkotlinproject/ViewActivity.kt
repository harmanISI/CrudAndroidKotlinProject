package com.example.androidkotlinproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.example.androidkotlinproject.db.UserTable
import com.example.androidkotlinproject.recyclerview.UserAdapter

class ViewActivity : AppCompatActivity() {
    companion object {
        var EXTRA_DATA_KEY = "hello"
    }
    private lateinit var searchId : EditText
    private lateinit var searchButton : Button
    private lateinit var userTable: UserTable

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        run {

            if (result.resultCode == Activity.RESULT_OK) {
                result.data
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        searchId = findViewById(R.id.view_searchText)
        searchButton = findViewById(R.id.view_searchButton)
        userTable = UserTable(this)
        val data = userTable.getAll()

        searchButton.setOnClickListener {
            if(searchId.text.toString() == "" )
            {
                Toast.makeText(this, "Any field should not be blank.!", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val value = searchId.text.toString()
                val desiredValue: Int = value.toInt()
                val result = userTable.get(desiredValue)
                if (result != null) {
                    Toast.makeText(this, result.firstName, Toast.LENGTH_SHORT).show()
                    val  intent = Intent(this, SearchData::class.java).apply {
                        putExtra(ViewActivity.EXTRA_DATA_KEY,"extradite")
                        putExtra("email",result.email)
                        putExtra("id",result.id.toString())
                        putExtra("lastName",result.lastName)
                        putExtra("firstname",result.firstName)
                        putExtra("course",result.course)
                    }
                    resultLauncher.launch(intent)
                }
                else{
                    Toast.makeText(this, "Data Not Found.!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val recyclerView = findViewById<RecyclerView>(R.id.view_data_recyclerView)
        recyclerView.adapter = UserAdapter(data)
        recyclerView.setHasFixedSize(true)
    }
}