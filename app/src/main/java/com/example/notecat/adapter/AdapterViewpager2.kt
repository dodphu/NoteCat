package com.example.notecat.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.notecat.fragments.AllNoteFragment

class AdapterViewpager2(fragmentActity:FragmentActivity): FragmentStateAdapter(fragmentActity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> AllNoteFragment()
            else -> AllNoteFragment()
        }
    }
}