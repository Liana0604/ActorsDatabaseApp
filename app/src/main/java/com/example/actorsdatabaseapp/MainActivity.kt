package com.example.actorsdatabaseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import com.example.actorsdatabaseapp.databinding.ActivityMainBinding
import com.example.actorsdatabaseapp.ui.BaseActivity
import com.example.actorsdatabaseapp.ui.BaseActivityForRoom

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DataBaseEnum.values().forEach {
            val button = AppCompatButton(this)
            button.text = it.name.replace("_", " ")
            button.tag = it
            binding.linearLayout.addView(button)
            button.setOnClickListener(this)
        }
    }


    override fun onClick(p0: View?) {
        when (p0?.tag as DataBaseEnum) {
            DataBaseEnum.ROOM -> startActivity(Intent(this, BaseActivityForRoom::class.java))
            DataBaseEnum.SQLITE -> startActivity(Intent(this, BaseActivity::class.java))
        }
    }
}

enum class DataBaseEnum {
    ROOM,
    SQLITE,
}