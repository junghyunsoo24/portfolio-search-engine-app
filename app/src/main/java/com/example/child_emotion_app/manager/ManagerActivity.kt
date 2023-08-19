package com.example.child_emotion_app.manager

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.child_emotion_app.child.AIChatActivity
import com.example.child_emotion_app.MypageActivity
import com.example.child_emotion_app.R
import com.example.child_emotion_app.databinding.ActivityExpertBinding
import com.example.child_emotion_app.databinding.ActivityManagerBinding
import com.example.child_emotion_app.expert.ChildListActivity
import com.example.child_emotion_app.expert.OneChildStaticsActivity
import com.example.child_emotion_app.model.AppViewModel

class ManagerActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val binding = ActivityManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = AppViewModel.getInstance()

        binding.checkExpertBtn.setOnClickListener {
            onExpertListButtonClicked()
        }

        binding.checkChildBtn.setOnClickListener {
            onNoAllocateChildListButtonClicked()
        }

        binding.childWithExpertBtn.setOnClickListener {
            onAllocateButtonClicked()
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

    fun onExpertListButtonClicked(){
        val intent = Intent(this, ExpertListActivity::class.java)
        startActivity(intent)
    }

    fun onNoAllocateChildListButtonClicked(){
        val intent = Intent(this, NoAllocateChildListActivity::class.java)
        startActivity(intent)
    }

    fun onAllocateButtonClicked(){
        val intent = Intent(this, AllocateListActivity::class.java)
        startActivity(intent)
    }
}