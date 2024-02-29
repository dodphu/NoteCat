package com.example.notecat.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewpager2.widget.ViewPager2
import com.example.notecat.R
import com.example.notecat.adapter.AdapterViewpager2
import com.example.notecat.databinding.ActivityMainBinding
import com.example.notecat.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager2: ViewPager2
    private lateinit var callback: ViewPager2.OnPageChangeCallback
    private val PREFS_NAME = "MyPrefsFile"
    private val NIGHT_MODE = "NightMode"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager2 = binding.viewpager2
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

        callback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> bottom_nav.menu.findItem(R.id.home_menu).isChecked = true
                    1 -> bottom_nav.menu.findItem(R.id.group_menu).isChecked = true
                    2 -> bottom_nav.menu.findItem(R.id.todo_menu).isChecked = true
                    3 -> bottom_nav.menu.findItem(R.id.setting_menu).isChecked = true
                }
            }
        }
        viewPager2.registerOnPageChangeCallback(callback)

        getNightMode()
    }

    private fun getNightMode() {
        val sharedPreferences = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isNightMode = sharedPreferences.getBoolean(NIGHT_MODE, false)
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager2.unregisterOnPageChangeCallback(callback)
    }
    fun setPager(position: Int) {
        viewPager2.currentItem = position
    }

}

