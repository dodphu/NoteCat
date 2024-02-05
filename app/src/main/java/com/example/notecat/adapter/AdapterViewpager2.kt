package com.example.notecat.adapter

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.notecat.activities.AddEditNoteActivity
import com.example.notecat.fragments.AllNoteFragment
import com.example.notecat.fragments.GroupsFragment
import com.example.notecat.fragments.SettingsFragment
import com.example.notecat.fragments.TodoFragment

class AdapterViewpager2(fragmentActity:FragmentActivity): FragmentStateAdapter(fragmentActity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> AllNoteFragment()
            1 -> GroupsFragment()
            2 -> TodoFragment()
            3 -> SettingsFragment()
            else -> AllNoteFragment()
        }
    }
}