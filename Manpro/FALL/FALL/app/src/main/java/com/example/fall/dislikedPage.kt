package com.example.fall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class dislikedPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disliked_page)

        // AMBIL DATA ARRAY LIST DARI DB DAN MASUKKAN KE RECYCLE VIEW
        val db = FirebaseFirestore.getInstance()
        val threadsRef = db.collection("threads").orderBy("dislike", Query.Direction.DESCENDING)
        var dataBundle = ArrayList<thread>()
//
        threadsRef.get()
            .addOnSuccessListener { querySnapshot ->
                // Iterate through the documents in the collection
                for (documentSnapshot in querySnapshot) {
                    // Ambil judul dari setiap document
                    val idGenre = documentSnapshot.getString("id_genre")
                    val idUser = documentSnapshot.getString("id_user")
                    val hirarki = documentSnapshot.getString("hirarki")
                    val judul = documentSnapshot.getString("judul")
                    val isi = documentSnapshot.getString("isi")
                    val like = documentSnapshot.getLong("like")
                    val dislike = documentSnapshot.getLong("dislike")
                    val date = documentSnapshot.getTimestamp("date")?.toDate()

                    // Tambahkan data ke dalam list
                    val newThread =
                        date?.let {
                            thread(idGenre, idUser, hirarki, judul, isi, like, dislike,
                                it
                            )
                        }

                    if (newThread != null) {
                        dataBundle.add(newThread)
                    }

                    Log.d("CEK DATA POP", "Data: $dataBundle")
                    val rvThread = findViewById<RecyclerView>(R.id.rvPop)
                    rvThread.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    val adapterRV = adapterthread(dataBundle)
                    rvThread.adapter = adapterRV

                }

//                _loading.visibility = View.INVISIBLE
            }
            .addOnFailureListener { exception ->
                // Handle error
            }
    }
}