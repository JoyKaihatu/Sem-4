package com.example.fall

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class Registration : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val btnNextRegist = findViewById<Button>(R.id.buttonNextRegist)
//        val regUser = findViewById<EditText>(R.id.editTextUsr)
        val regUser = findViewById<EditText>(R.id.regUser)
        val regPass = findViewById<EditText>(R.id.regPass)
        val regAge = findViewById<EditText>(R.id.regAge)


//        CoroutineScope(Dispatchers.Main).async {
            btnNextRegist.setOnClickListener{

                if (regUser.text.toString() != "" || regPass.text.toString() != "") {
                    val regist2Intent = Intent(this@Registration, Registration2::class.java)
                    regist2Intent.putExtra("age", regAge.text.toString())
                    regist2Intent.putExtra("user", regUser.text.toString())
                    regist2Intent.putExtra("pass", regPass.text.toString())
                    startActivity(regist2Intent)
                } else {
                    Log.d("FAILED", "gagal next")
                    AlertDialog.Builder(this@Registration)
                        .setTitle("DATA NOT FILLED")
                        .setMessage("Back to fill data!")
                        .setNeutralButton("Back",
                        DialogInterface.OnClickListener{dialog, which ->
                            Toast.makeText(this@Registration, "Mohon lengkapi data di atas", Toast.LENGTH_LONG).show()
                        })
                }
//            }
        }
    }
}