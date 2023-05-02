package com.example.fall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class AddCommentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_comment)
        val db = FirebaseFirestore.getInstance()
        val getThread = intent.getStringExtra("THREADS")
        val threadsRef = db.collection("threads")
        val docRef = threadsRef.document(getThread.toString())
        val listThread = HashMap<String, Any>()
        val sharedPreferences = getSharedPreferences("SessionUser", MODE_PRIVATE)
        val savedName = sharedPreferences.getString("id_user", "")
        val isiComment = findViewById<EditText>(R.id.isiComment)
        val btnPostComment = findViewById<Button>(R.id.buttonPostThreadNew)
        val calendar = Calendar.getInstance()

        // Mendapatkan waktu sekarang dalam bentuk tanggal
        val currentDate = calendar.time

        btnPostComment.setOnClickListener {
            docRef.get()
                .addOnSuccessListener { anjer ->
                    val idGenre = anjer.getString("id_genre")
                    val judul = anjer.getString("judul")

                    listThread["date"] = currentDate
                    listThread["id_genre"] = idGenre.toString()
                    listThread["id_user"] = savedName.toString()
                    listThread["hirarki"] = getThread.toString()
                    listThread["judul"] = judul.toString()
                    listThread["isi"] = isiComment.text.toString()
                    listThread["like"] = 0
                    listThread["dislike"] = 0

                    db.collection("threads").add(listThread)
                }
            startActivity(Intent(this@AddCommentActivity, CommentActivity::class.java).apply {
                putExtra("THREADS", getThread)
            })
        }

    }
}