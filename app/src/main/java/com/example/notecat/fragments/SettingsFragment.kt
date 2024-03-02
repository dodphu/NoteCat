package com.example.notecat.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notecat.R
import com.example.notecat.activities.SignUpActivity
import com.example.notecat.databinding.FragmentSettingsBinding
import com.example.notecat.viewmodel.NoteViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


class SettingsFragment : Fragment(R.layout.fragment_settings) {
    interface NightModeChangeListener {
        fun onNightModeChanged(isNightModeEnabled: Boolean)
    }

    private val PREFS_NAME = "MyPrefsFile"
    private val NIGHT_MODE = "NightMode"
    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser = firebaseAuth.currentUser
    val emailuser = firebaseUser?.email
    val firebaseStore = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSettingsBinding.bind(view)

        val txtuser = binding.txtUserSettings
        val switchmode = binding.switchnightmode
        val switchdongbo = binding.switchsavefirebase
        val linearlogin = binding.linearLogin
        val linearlogout = binding.linearLogout


        val sharedPreferences =
            requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isNightMode = sharedPreferences.getBoolean(NIGHT_MODE, false)
        val noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]


        switchmode.isChecked = isNightMode

        switchmode.setOnCheckedChangeListener { _, isChecked ->
            saveNightModeState(isChecked)
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        if (emailuser != null) {
            txtuser.text = emailuser
            linearlogin.visibility = View.GONE
            linearlogout.visibility = View.VISIBLE
            switchdongbo.isEnabled = true
        } else {
            txtuser.text = "Khách"
            linearlogin.visibility = View.VISIBLE
            linearlogout.visibility = View.GONE
            switchdongbo.isChecked = false
            switchdongbo.isEnabled = false

        }

        linearlogin.setOnClickListener {
            val intent = Intent(activity, SignUpActivity::class.java)
            startActivity(intent)
        }

        linearlogout.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
            builder.apply {
                setTitle("Đăng xuất")
                setMessage("Bạn có muốn đăng xuất không?")
                setPositiveButton("Đăng xuất") { dialog, id ->
                    firebaseAuth.signOut()
                    txtuser.text = "Khách"
                    linearlogin.visibility = View.VISIBLE
                    linearlogout.visibility = View.GONE
                    switchdongbo.isChecked = false
                    noteViewModel.getAllNotesVM().observe(viewLifecycleOwner) { listNotes ->
                        val noteToDelete = listNotes.firstOrNull()
                        noteToDelete?.let {
                            noteViewModel.deleteNoteVM(it)
                        }
                        requireActivity().finishAffinity()

                    }

                }
                setNegativeButton("Hủy") { dialog, id ->

                }
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }

        switchdongbo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                noteViewModel.getAllNotesVM().observe(viewLifecycleOwner) { listNotes ->
                    // Lưu trữ danh sách các ghi chú vào Firestore
                    listNotes.forEachIndexed { index, note ->
                        firebaseStore.collection("AllNotes")
                            .document("$emailuser")
                            .collection("Notes")
                            .document("note$index")
                            .set(note)
                            .addOnSuccessListener {
                                Log.d("phu", "Ghi chú đã được lưu vào Firestore.")
                            }
                            .addOnFailureListener { e ->
                                Log.e("phu", "Lỗi khi lưu ghi chú vào Firestore: $e")
                            }
                    }
                }
                Toast.makeText(requireContext(), "Đã lưu lên hệ thống !", Toast.LENGTH_SHORT).show()
                Thread.sleep(1000)
                switchdongbo.isChecked = false
            }

        }

    }


    private fun saveNightModeState(isNightMode: Boolean) {
        val sharedPreferences =
            requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(NIGHT_MODE, isNightMode)
        editor.apply()
    }


}