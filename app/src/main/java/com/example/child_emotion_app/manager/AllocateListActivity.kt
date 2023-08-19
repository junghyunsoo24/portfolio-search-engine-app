package com.example.child_emotion_app.manager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.child_emotion_app.MypageActivity
import com.example.child_emotion_app.R
import com.example.child_emotion_app.data.allocate.AllocateList
import com.example.child_emotion_app.databinding.ActivityAllocateListBinding
import com.example.child_emotion_app.service.allocate.AllocateApi
import kotlinx.coroutines.launch

class AllocateListActivity : AppCompatActivity(){
    private lateinit var child_id: String
    private lateinit var expert_id: String


    private lateinit var text: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAllocateListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        binding.allocateBtn.setOnClickListener {
            child_id = binding.allocateChildIdInput.text.toString()
            expert_id = binding.allocateExpertIdInput.text.toString()


            mobileToServer()
        }
    }


    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(message)
        builder.setMessage("아동 할당 성공")

        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun mobileToServer() {
        lifecycleScope.launch {
            try {
                val message = AllocateList(child_id, expert_id)
                val response = AllocateApi.retrofitService.sendsMessage(message)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mypage_btn -> {
                val intent = Intent(this, MypageActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}