package com.example.child_emotion_app.expert

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.child_emotion_app.data.childList.Child
import com.example.child_emotion_app.data.childList.ChildList
import com.example.child_emotion_app.databinding.ActivityChildListBinding
import com.example.child_emotion_app.model.AppViewModel
import com.example.child_emotion_app.service.childList.ChildListApi
import kotlinx.coroutines.launch

class ChildListActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel
    private lateinit var binding: ActivityChildListBinding

    private lateinit var result: List<Child>
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChildListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        viewModel = AppViewModel.getInstance()

        id = viewModel.getUserId().value.toString()

        mobileToServer()
    }

    private fun mobileToServer() {
        lifecycleScope.launch {
            try {
                val message = ChildList(id)
                val response = ChildListApi.retrofitService.sendsMessage(message)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // 서버 응답을 확인하는 작업 수행
                        val responseData = responseBody.child
                        result = responseData

                        // 문자열 형태로 결과 값을 구성
                        val resultText = StringBuilder()
                        for (child in result) {
                            val childInfo = "이름: ${child.name}\n 아이디: ${child.id}\n 비밀번호: ${child.pw}\n" +
                                    "이메일: ${child.email}\n 상태: ${child.institution}\n"
                            resultText.append(childInfo).append("\n")
                        }

                        binding.childListResponse.text = resultText.toString()

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