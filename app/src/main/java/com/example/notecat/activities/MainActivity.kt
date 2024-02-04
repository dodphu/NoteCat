package com.example.notecat.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.notecat.R
import com.example.notecat.adapter.AdapterViewpager2
import com.example.notecat.databinding.ActivityMainBinding
import com.example.notecat.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager2 = binding.viewpager2
        val bottom_nav = binding.navBottomnavigation
        val adapterViewPager2 = AdapterViewpager2(this)
        viewPager2.adapter = adapterViewPager2

        bottom_nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_menu -> viewPager2.currentItem = 0
                R.id.group_menu -> viewPager2.currentItem = 1
                R.id.todo_menu -> viewPager2.currentItem = 2
                R.id.setting_menu -> viewPager2.currentItem = 3
            }
            true
        }
    }
}