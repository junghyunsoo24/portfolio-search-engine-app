package com.example.child_emotion_app.manager

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
import com.example.child_emotion_app.adapter.NoAllocateChildListAdapter
import com.example.child_emotion_app.data.noAllocateChildList.Child

import com.example.child_emotion_app.databinding.ActivityNoAllocateChildListBinding
import com.example.child_emotion_app.model.AppViewModel
import com.example.child_emotion_app.service.childList.NoAllocateChildListApi
import kotlinx.coroutines.launch

class NoAllocateChildListActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel
    private lateinit var binding: ActivityNoAllocateChildListBinding

    private lateinit var result: List<Child>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoAllocateChildListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        viewModel = AppViewModel.getInstance()

        val layoutManager = LinearLayoutManager(this)
        binding.noAllocateChildRecyclerView.layoutManager = layoutManager
        val adapter = NoAllocateChildListAdapter(emptyList())
        binding.noAllocateChildRecyclerView.adapter = adapter

        mobileToServer()
    }

    private fun mobileToServer() {
        lifecycleScope.launch {
            try {
                val response = NoAllocateChildListApi.retrofitService.sendsMessage()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // 서버 응답을 확인하는 작업 수행
                        val responseData = responseBody.child
                        result = responseData

                        val adapter =
                            binding.noAllocateChildRecyclerView.adapter as NoAllocateChildListAdapter
                        adapter.noAllocateChildList = result // 어댑터에 데이터 설정
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