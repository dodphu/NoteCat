package com.example.notecat.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.notecat.R
import com.example.notecat.databinding.ActivityFirstBinding
import com.example.notecat.databinding.ActivityUserPassLoginBinding
import com.example.notecat.model.Note
import com.example.notecat.viewmodel.NoteViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserPassLoginActivity : AppCompatActivity() {
    private val PREFS_NAME = "MyPrefsFile"
    private val NIGHT_MODE = "NightMode"

    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseStore = FirebaseFirestore.getInstance()
    val firebaseUser = firebaseAuth.currentUser
    val emailuser = firebaseUser?.email


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUserPassLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val emailLogin = binding.txtEmailLogin
        val passlogin = binding.txtPassLogin
        val btnloginz = binding.btnLoginLogin
        val cbxsavetk = binding.cbxAutologin
        val btnsignup = binding.btnLoginSignuupp

        btnloginz.setOnClickListener {
            try {
                login(emailLogin.text.toString(), passlogin.text.toString(), cbxsavetk)
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
        btnsignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
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

    fun login(email: String, password: String, cbxcheck: CheckBox) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = firebaseAuth.currentUser
                    if (firebaseUser != null && !firebaseUser.isEmailVerified) {
                        firebaseUser.sendEmailVerification()
                            .addOnCompleteListener { verifiedTask ->
                                Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()
                            }
                    } else if (firebaseUser != null && firebaseUser.isEmailVerified) {
                        Toast.makeText(this, "Login Complete", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        loaddata()
                        finish()
                    }
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun loaddata() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        firebaseUser?.let { user ->
            val emailuser = user.email
            emailuser?.let { email ->
                Log.d("phu", "loaddata: $email")


                val noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

                firebaseStore.collection("AllNotes")
                    .document(email)
                    .collection("Notes")
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            val id = document.getLong("id")?.toInt()
                            val title = document.getString("title")
                            val content = document.getString("content")
                            val date = document.getString("date")
                            val color = document.getLong("color")?.toInt()

                            val note = Note(id, title!!, content!!, date, color)
                            Log.d("phu", "loaddata: $note")
                            noteViewModel.addNoteVM(note)

                        }

                    }
                    .addOnFailureListener { e ->
                        Log.e("Error", "Error fetching documents: $e")
                    }

            } ?: Log.e("phu", "Không tìm thấy email của người dùng")
        } ?: Log.e("phu", "Không có người dùng đang đăng nhập")
    }

}