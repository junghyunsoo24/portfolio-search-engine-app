package com.example.child_emotion_app.child

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.child_emotion_app.LoginActivity
import com.example.child_emotion_app.R
import com.example.child_emotion_app.data.regist.Regist
import com.example.child_emotion_app.databinding.ActivityRegistBinding
import com.example.child_emotion_app.service.regist.RegistApi
import kotlinx.coroutines.launch

class RegistActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var id: String
    private lateinit var pw: String
    private lateinit var name: String
    private lateinit var age: String
    private lateinit var address: String
    private lateinit var gender: String
    private lateinit var phone_num: String

    private lateinit var genderValue: String
    private lateinit var text: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRegistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val genders = arrayOf("남", "여")
        val spinner = findViewById<Spinner>(R.id.spinner_gender)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        binding.registBtn.setOnClickListener {
            id = binding.firstIdInput.text.toString()
            pw = binding.firstPwdInput.text.toString()
            name = binding.nameInput.text.toString()
            age = binding.ageInput.text.toString()
            address = binding.addressInput.text.toString()
            gender?.let { selectedGender ->
                genderValue = if (selectedGender == "남") "0" else "1"
            }
            phone_num = binding.addressInput.text.toString()

            mobileToServer()
        }
    }

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: android.view.View?,
        position: Int,
        id: Long
    ) {
        gender = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    fun onRegistButtonClicked() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(message)
        builder.setMessage("회원가입 성공")

        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss()
            onRegistButtonClicked()
        }

        builder.show()
    }

    private fun mobileToServer() {
        lifecycleScope.launch {
            try {
                val message = Regist(id, pw, name, age, address, genderValue, phone_num)
                val response = RegistApi.retrofitService.sendsMessage(message)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // 서버 응답을 확인하는 작업 수행
                        val responseData = responseBody.result

                        showAlertDialog(responseData)

                    } else {
                        Log.e("@@@@Error3", "Response body is null")
                    }
                } else {
                    Log.e("@@@@Error2", "Response not successful: ${response.code()}")
                }
            } catch (Ex: Exception) {
                Log.e("@@@@Error1", Ex.stackTraceToString())
            }
        }
    }
}