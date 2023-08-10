package com.example.child_emotion_app.expert

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.child_emotion_app.MypageActivity
import com.example.child_emotion_app.R
import com.example.child_emotion_app.adapter.ChildListAdapter
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

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        val adapter = ChildListAdapter(emptyList())
        binding.recyclerView.adapter = adapter

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

                        val adapter = binding.recyclerView.adapter as ChildListAdapter
                        adapter.childList = result // 어댑터에 데이터 설정
                        adapter.notifyDataSetChanged()

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