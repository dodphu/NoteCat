package com.example.notecat.fragments


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.notecat.R
import com.example.notecat.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment(R.layout.fragment_settings) {
    interface NightModeChangeListener {
        fun onNightModeChanged(isNightModeEnabled: Boolean)
    }
    private val PREFS_NAME = "MyPrefsFile"
    private val NIGHT_MODE = "NightMode"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSettingsBinding.bind(view)
        val switchmode = binding.switchnightmode

        val sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isNightMode = sharedPreferences.getBoolean(NIGHT_MODE, false)

        switchmode.isChecked = isNightMode

        switchmode.setOnCheckedChangeListener { _, isChecked ->
            saveNightModeState(isChecked)
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun saveNightModeState(isNightMode: Boolean) {
        val sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(NIGHT_MODE, isNightMode)
        editor.apply()
    }
}