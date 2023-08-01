package com.example.child_emotion_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar

class RegistActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var number = -1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regist)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val gender = arrayOf("남", "여")
        val spinner = findViewById<Spinner>(R.id.spinner_gender)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, gender)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        val start : Button = findViewById(R.id.regist_btn)

        start.setOnClickListener {
            onRegistButtonClicked()
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p2){
            0 -> number = 1
            1 -> number = 0
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    fun onRegistButtonClicked(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}