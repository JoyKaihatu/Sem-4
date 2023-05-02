package com.example.fall

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class CommentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val _tJudul = findViewById<TextView>(R.id.tJudul)
        val _tIsi = findViewById<TextView>(R.id.tIsi)
        val _tLike = findViewById<TextView>(R.id.tLike)
        val _tDis = findViewById<TextView>(R.id.tDis)
        val btnComment2 = findViewById<ImageView>(R.id.imageViewComment)
        val _totalThumbsUp = findViewById<ImageView>(R.id.imageView9)
        val _totalThumbsDown = findViewById<ImageView>(R.id.imageView10)

        val sharedPreferences = getSharedPreferences("SessionUser", Context.MODE_PRIVATE)
        val savedName = sharedPreferences.getString("id_user", "")


        val db = FirebaseFirestore.getInstance()
//        Log.d("JUDUL", thread.judul?)
        val getThread = intent.getStringExtra("THREADS")
//        val listView = findViewById<ListView>(R.id.listviewComment)
//        val adapter = ArrayAdapter<thread>(this, android.R.layout.simple_list_item_1)
//        listView.adapter = adapter

        val threadsRef = db.collection("threads")
        val docRef = threadsRef.document(getThread.toString())
        docRef.get()
            .addOnSuccessListener { anjer ->
                    val idGenre = anjer.getString("id_genre")
                    val idUser = anjer.getString("id_user")
                    val judul = anjer.getString("judul")
                    val isi = anjer.getString("isi")
                    val like = anjer.getLong("like")
                    val dislike = anjer.getLong("dislike")
                    val date = anjer.getTimestamp("date")?.toDate()

                    _tJudul.setText(judul)
                    _tIsi.setText(isi)
                    _tLike.setText(like.toString())
                    _tDis.setText(dislike.toString())

                    Log.d("CEK JUDUL C", _tJudul.text.toString())
                }

        val threadsReftub = db.collection("thumbs").whereEqualTo("id_threads", getThread.toString()).whereEqualTo("id_user", savedName)

        threadsReftub.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.isEmpty) {

                } else {
                    // Iterate through the documents in the snapshot
                    for (document in documentSnapshot) {
                        val type = document.getString("type")

                        if (type == "up"){
                            _totalThumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                            _totalThumbsDown.setImageResource(R.drawable.ic_baseline_thumb_down_24_nonact)
                        } else if( type == "down"){
                            _totalThumbsDown.setImageResource(R.drawable.ic_baseline_thumb_down_24)
                            _totalThumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24_nonact)

                        }
                        // do something with the type
                    }
                }
            }
            .addOnFailureListener { exception ->
                // Gagal ambil data
            }

        _totalThumbsUp.setOnClickListener {
            val threadsRef2 =
                db.collection("thumbs").whereEqualTo("id_threads", getThread.toString())
                    .whereEqualTo("id_user", savedName)

            threadsRef2.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.isEmpty) {
                        val tu = hashMapOf(
                            "id_threads" to getThread.toString(),
                            "id_user" to savedName,
                            "type" to "up"
                        )
                        db.collection("thumbs").add(tu)

                        val threadsReftu = db.collection("threads")
                        val docReftu = threadsReftu.document(getThread.toString())

                        docReftu.get()
                            .addOnSuccessListener { documentSnapshot4 ->
                                val like = documentSnapshot4.getLong("like")
                                docReftu.update("like", like!! + 1)
                                    .addOnSuccessListener {
                                        // Update berhasil
                                        _totalThumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                                        _tLike.text = (like + 1).toString()
                                    }
                                    .addOnFailureListener { exception ->
                                        // Update gagal
                                    }
                            }
                            .addOnFailureListener { exception ->
                                // Gagal ambil data
                            }

                    } else {
                        // Iterate through the documents in the snapshot
                        for (document in documentSnapshot) {
                            val type = document.getString("type")
                            val idThumbs = document.id

                            if (type == "up") {
                                val threadsRef3 = db.collection("threads")
                                val docRef3 = threadsRef3.document(getThread.toString())

                                docRef3.get()
                                    .addOnSuccessListener { documentSnapshot2 ->
                                        val like = documentSnapshot2.getLong("like")
                                        docRef3.update("like", like!! - 1)
                                            .addOnSuccessListener {
                                                // Update berhasil
                                                _totalThumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24_nonact)
                                                _tLike.text =
                                                    (like - 1).toString()

                                                db.collection("thumbs")
                                                    .document(idThumbs).delete()
                                            }
                                            .addOnFailureListener { exception ->
                                                // Update gagal
                                            }
                                    }
                                    .addOnFailureListener { exception ->
                                        // Gagal ambil data
                                    }
                                //                                                        _totalThumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                            } else if (type == "down") {
                                val threadsRef3 = db.collection("threads")
                                val docRef3 = threadsRef3.document(getThread.toString())

                                docRef3.get()
                                    .addOnSuccessListener { documentSnapshot2 ->
                                        val dislike =
                                            documentSnapshot2.getLong("dislike")
                                        val like = documentSnapshot2.getLong("like")

                                        docRef3.update("dislike", dislike!! - 1)
                                            .addOnSuccessListener {
                                                // Update berhasil
                                                _totalThumbsDown.setImageResource(
                                                    R.drawable.ic_baseline_thumb_down_24_nonact
                                                )
                                                _tDis.text =
                                                    (dislike - 1).toString()
                                            }
                                            .addOnFailureListener { exception ->
                                                // Update gagal
                                            }
                                        docRef3.update("like", like!! + 1)
                                            .addOnSuccessListener {
                                                // Update berhasil
                                                _totalThumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                                                _tLike.text =
                                                    (like + 1).toString()
                                                db.collection("thumbs")
                                                    .document(idThumbs)
                                                    .update("type", "up")
                                            }
                                            .addOnFailureListener { exception ->
                                                // Update gagal
                                            }


                                    }
                                    .addOnFailureListener { exception ->
                                        // Gagal ambil data
                                    }
                            }
                            // do something with the type
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    // Gagal ambil data
                }



        }

        _totalThumbsDown.setOnClickListener {
            val threadsRef2 =
                db.collection("thumbs").whereEqualTo("id_threads", getThread.toString())
                    .whereEqualTo("id_user", savedName)

            threadsRef2.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.isEmpty) {
                        val tu = hashMapOf(
                            "id_threads" to getThread.toString(),
                            "id_user" to savedName,
                            "type" to "down"
                        )
                        db.collection("thumbs").add(tu)

                        val threadsReftd = db.collection("threads")
                        val docReftd = threadsReftd.document(getThread.toString())

                        docReftd.get()
                            .addOnSuccessListener { documentSnapshot4 ->
                                val dislike = documentSnapshot4.getLong("dislike")
                                docReftd.update("dislike", dislike!! + 1)
                                    .addOnSuccessListener {
                                        // Update berhasil
                                        _totalThumbsDown.setImageResource(R.drawable.ic_baseline_thumb_down_24)
                                        _tDis.text = (dislike + 1).toString()
                                    }
                                    .addOnFailureListener { exception ->
                                        // Update gagal
                                    }
                            }
                            .addOnFailureListener { exception ->
                                // Gagal ambil data
                            }

                    } else {
                        // Iterate through the documents in the snapshot
                        for (document in documentSnapshot) {
                            val type = document.getString("type")
                            val idThumbs1 = document.id


                            if (type == "up") {
                                val threadsRef3 = db.collection("threads")
                                val docRef3 = threadsRef3.document(getThread.toString())

                                docRef3.get()
                                    .addOnSuccessListener { documentSnapshot2 ->
                                        val dislike =
                                            documentSnapshot2.getLong("dislike")
                                        val like = documentSnapshot2.getLong("like")

                                        docRef3.update("like", like!! - 1)
                                            .addOnSuccessListener {
                                                // Update berhasil
                                                _totalThumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24_nonact)
                                                _tLike.text =
                                                    (like - 1).toString()
                                            }
                                            .addOnFailureListener { exception ->
                                                // Update gagal
                                            }
                                        docRef3.update("dislike", dislike!! + 1)
                                            .addOnSuccessListener {
                                                // Update berhasil
                                                _totalThumbsDown.setImageResource(
                                                    R.drawable.ic_baseline_thumb_down_24
                                                )
                                                _tDis.text =
                                                    (dislike + 1).toString()
                                                db.collection("thumbs")
                                                    .document(idThumbs1)
                                                    .update("type", "down")

                                            }
                                            .addOnFailureListener { exception ->
                                                // Update gagal
                                            }
                                    }
                                    .addOnFailureListener { exception ->
                                        // Gagal ambil data
                                    }


//                                                        _totalThumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                            } else if (type == "down") {
                                val threadsRef3 = db.collection("threads")
                                val docRef3 = threadsRef3.document(getThread.toString())

                                docRef3.get()
                                    .addOnSuccessListener { documentSnapshot2 ->
                                        val dislike =
                                            documentSnapshot2.getLong("dislike")
                                        docRef3.update("dislike", dislike!! - 1)
                                            .addOnSuccessListener {
                                                // Update berhasil
                                                _totalThumbsDown.setImageResource(
                                                    R.drawable.ic_baseline_thumb_down_24_nonact
                                                )
                                                _tDis.text =
                                                    (dislike - 1).toString()
                                                db.collection("thumbs")
                                                    .document(idThumbs1).delete()
                                            }
                                            .addOnFailureListener { exception ->
                                                // Update gagal
                                            }
                                    }
                                    .addOnFailureListener { exception ->
                                        // Gagal ambil data
                                    }
                            }
                            // do something with the type
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    // Gagal ambil data
                }

        }




        val colRef = db.collection("threads").whereEqualTo("hirarki", getThread).orderBy("date", Query.Direction.DESCENDING)
        var dataBundle = ArrayList<thread>()
        colRef.get()
            .addOnSuccessListener { anjer ->
                for (anjers in anjer) {
                    val idGenre = anjers.getString("id_genre")
                    val idUser = anjers.getString("id_user")
                    val hirarki = anjers.getString("hirarki")
                    val judul = anjers.getString("judul")
                    val isi = anjers.getString("isi")
                    val like = anjers.getLong("like")
                    val dislike = anjers.getLong("dislike")
                    val date = anjers.getTimestamp("date")?.toDate()


//                    adapter.add(date?.let {
//                        thread(idGenre, idUser, hirarki, judul, isi, like, dislike,
//                            it
//                        )
//                    })
//                    adapter.notifyDataSetChanged()
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

                    Log.d("CEK DATA COMMENT", "Data: $dataBundle")
                    val rvThread = findViewById<RecyclerView>(R.id.rvComment)
                    rvThread.layoutManager = LinearLayoutManager(this)
                    val adapterRV = adapterthread(dataBundle)
                    rvThread.adapter = adapterRV

                }

            }



        btnComment2.setOnClickListener {
            val colRef2 = db.collection("threads").whereEqualTo("judul", _tJudul.text.toString()).whereEqualTo("isi", _tIsi.text.toString())
            colRef2.get()
                .addOnSuccessListener { anjer ->
                    for (anjers in anjer){
                        val id = anjers.id
                       startActivity(Intent(this, AddCommentActivity::class.java).apply {
                            putExtra("THREADS", id)
                        })
                    }
                }
        }

        Log.d("ID THREADS", getThread.toString())

    }
}