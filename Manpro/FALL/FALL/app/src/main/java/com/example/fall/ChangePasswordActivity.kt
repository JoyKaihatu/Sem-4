package com.example.fall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val sp = getSharedPreferences("SessionUser", MODE_PRIVATE)
        val savedName = sp.getString("id_user", "")

        val db = FirebaseFirestore.getInstance()

        val btnSavePass = findViewById<Button>(R.id.buttonSavePass)
        val etNewPass =  findViewById<EditText>(R.id.editTextNewPass)
        val etRePass = findViewById<EditText>(R.id.editTextRePass)


        btnSavePass.setOnClickListener {
//            UBAH DAN SAVE PASSWORD BARU KE DB
            if (etNewPass.text.toString() == etRePass.text.toString()){
                val id = db.collection("account").document(savedName.toString())
                id.update("password", etNewPass.text.toString())
                    .addOnSuccessListener {
                        etNewPass.setText("")
                        etRePass.setText("")
                        startActivity(Intent(this@ChangePasswordActivity, tesHome::class.java))
                    }
            }
            else{
                etRePass.setText("")
                Toast.makeText(this, "Mohon Periksa Kembali Password!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}