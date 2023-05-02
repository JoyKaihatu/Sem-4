package com.example.fall

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class adapterthread(
    private val listThread: ArrayList<thread>?
) : RecyclerView.Adapter<adapterthread.ListViewHolder>(){

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var _judulThread :TextView = itemView.findViewById(R.id.tJudul)
        var _isiThread : TextView = itemView.findViewById(R.id.tIsi)
        var _totLike : TextView = itemView.findViewById(R.id.tLike)
        var _totDis : TextView = itemView.findViewById(R.id.tDis)
        val _dateTh: TextView = itemView.findViewById(R.id.dateTh)
        val _cardThread: View = itemView.findViewById(R.id.cardView5)
        val _comment: ImageView = itemView.findViewById(R.id.imageViewComment)
        var _totalThumbsUp : ImageView = itemView.findViewById(R.id.imageView9)
        var _totalThumbsDown : ImageView = itemView.findViewById(R.id.imageView10)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback
    interface OnItemClickCallback{
        fun editThread(pos: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.threaditem, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var thread = listThread!!.get(position)
        val db = FirebaseFirestore.getInstance()

        val sharedPreferences = holder.itemView.context.getSharedPreferences("SessionUser", Context.MODE_PRIVATE)
        val savedName = sharedPreferences.getString("id_user", "")

//        Log.d("THREAD_DATA", "Judul: ${thread.judul}, Like: ${thread.like}, Dislike: ${thread.dislike}")

        holder._judulThread.setText(thread.judul)
        holder._isiThread.setText(thread.isi)
        holder._totLike.setText(thread.like.toString())
        holder._totDis.setText(thread.dislike.toString())
        holder._dateTh.setText(thread.date.toString())


        val colRefthumb = db.collection("threads").whereEqualTo("judul", thread.judul).whereEqualTo("isi",thread.isi).whereEqualTo("date", thread.date)
        colRefthumb.get()
            .addOnSuccessListener { anjer ->
                for (anjers in anjer){
                    val id = anjers.id
                    val threadsRef = db.collection("thumbs").whereEqualTo("id_threads", id.toString()).whereEqualTo("id_user", savedName)

                    threadsRef.get()
                        .addOnSuccessListener { documentSnapshot ->
                            if (documentSnapshot.isEmpty) {

                            } else {
                                // Iterate through the documents in the snapshot
                                for (document in documentSnapshot) {
                                    val type = document.getString("type")

                                    if (type == "up"){
                                        holder._totalThumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                                        holder._totalThumbsDown.setImageResource(R.drawable.ic_baseline_thumb_down_24_nonact)
                                    } else if( type == "down"){
                                        holder._totalThumbsDown.setImageResource(R.drawable.ic_baseline_thumb_down_24)
                                        holder._totalThumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24_nonact)

                                    }
                                    // do something with the type
                                }
                            }
                        }
                        .addOnFailureListener { exception ->
                            // Gagal ambil data
                        }
                }
            }

//        Log.d("JUDUL", thread.judul?)
        holder._cardThread.setOnClickListener {
//            onItemClickCallback.editThread(position)
//            Log.d("WOE", "PLIS")
            val colRef = db.collection("threads").whereEqualTo("judul", thread.judul).whereEqualTo("isi",thread.isi).whereEqualTo("date", thread.date)
            colRef.get()
                .addOnSuccessListener { anjer ->
                    for (anjers in anjer){
                        val id = anjers.id
                        holder._cardThread.context.startActivity(Intent(holder._cardThread.context, CommentActivity::class.java).apply {
                            putExtra("THREADS", id)
                        })
                    }
                }
        }

//        holder._totalThumbsUp.setOnClickListener {
//            val threadsRef = db.collection("threads").document(thread.id)
//            threadsRef.get()
//                .addOnSuccessListener { documentSnapshot ->
//                    val like = documentSnapshot.getLong("like")
//                    threadsRef.update("like", like!! + 1)
//                        .addOnSuccessListener {
//                            // Update berhasil
//                            holder._totalThumbsUp.setImageResource(R.drawable.ic_thumb_up_clicked)
//                            holder._totLike.text = (like + 1).toString()
//                        }
//                        .addOnFailureListener { exception ->
//                            // Update gagal
//                        }
//                }
//                .addOnFailureListener { exception ->
//                    // Gagal ambil data
//                }
//        }
//




        holder._totalThumbsUp.setOnClickListener {


            val docRef2 = db.collection("threads").whereEqualTo("judul", thread.judul).whereEqualTo("isi",thread.isi).whereEqualTo("date", thread.date)

            docRef2.get()
                .addOnSuccessListener { anjer2 ->
                    for (anjers2 in anjer2) {
                        val id = anjers2.id
                        val threadsRef2 =
                            db.collection("thumbs").whereEqualTo("id_threads", id.toString())
                                .whereEqualTo("id_user", savedName)
                        //

                        threadsRef2.get()
                            .addOnSuccessListener { documentSnapshot ->
                                if (documentSnapshot.isEmpty) {
                                    val tu = hashMapOf(
                                        "id_threads" to id,
                                        "id_user" to savedName,
                                        "type" to "up"
                                    )
                                    db.collection("thumbs").add(tu)

                                    val threadsRef = db.collection("threads")
                                    val docRef = threadsRef.document(id.toString())

                                    docRef.get()
                                        .addOnSuccessListener { documentSnapshot4 ->
                                            val like = documentSnapshot4.getLong("like")
                                            docRef.update("like", like!! + 1)
                                                .addOnSuccessListener {
                                                    // Update berhasil
                                                    holder._totalThumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                                                    holder._totLike.text = (like + 1).toString()
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
                                            val docRef3 = threadsRef3.document(id.toString())

                                            docRef3.get()
                                                .addOnSuccessListener { documentSnapshot2 ->
                                                    val like = documentSnapshot2.getLong("like")
                                                    docRef3.update("like", like!! - 1)
                                                        .addOnSuccessListener {
                                                            // Update berhasil
                                                            holder._totalThumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24_nonact)
                                                            holder._totLike.text =
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
                                            //                                                        holder._totalThumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                                        } else if (type == "down") {
                                            val threadsRef3 = db.collection("threads")
                                            val docRef3 = threadsRef3.document(id.toString())

                                            docRef3.get()
                                                .addOnSuccessListener { documentSnapshot2 ->
                                                    val dislike =
                                                        documentSnapshot2.getLong("dislike")
                                                    val like = documentSnapshot2.getLong("like")

                                                    docRef3.update("dislike", dislike!! - 1)
                                                        .addOnSuccessListener {
                                                            // Update berhasil
                                                            holder._totalThumbsDown.setImageResource(
                                                                R.drawable.ic_baseline_thumb_down_24_nonact
                                                            )
                                                            holder._totDis.text =
                                                                (dislike - 1).toString()
                                                        }
                                                        .addOnFailureListener { exception ->
                                                            // Update gagal
                                                        }
                                                    docRef3.update("like", like!! + 1)
                                                        .addOnSuccessListener {
                                                            // Update berhasil
                                                            holder._totalThumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                                                            holder._totLike.text =
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
                }

        }

        holder._totalThumbsDown.setOnClickListener {

            val docRef22 = db.collection("threads").whereEqualTo("judul", thread.judul)
                .whereEqualTo("isi", thread.isi).whereEqualTo("date", thread.date)

            docRef22.get()
                .addOnSuccessListener { anjer2 ->
                    for (anjers2 in anjer2) {
                        val id = anjers2.id
                        val threadsRef2 =
                            db.collection("thumbs").whereEqualTo("id_threads", id.toString())
                                .whereEqualTo("id_user", savedName)

                        threadsRef2.get()
                            .addOnSuccessListener { documentSnapshot ->
                                if (documentSnapshot.isEmpty) {
                                    val tu = hashMapOf(
                                        "id_threads" to id,
                                        "id_user" to savedName,
                                        "type" to "down"
                                    )
                                    db.collection("thumbs").add(tu)

                                    val threadsRef = db.collection("threads")
                                    val docRef = threadsRef.document(id.toString())

                                    docRef.get()
                                        .addOnSuccessListener { documentSnapshot4 ->
                                            val dislike = documentSnapshot4.getLong("dislike")
                                            docRef.update("dislike", dislike!! + 1)
                                                .addOnSuccessListener {
                                                    // Update berhasil
                                                    holder._totalThumbsDown.setImageResource(R.drawable.ic_baseline_thumb_down_24)
                                                    holder._totDis.text = (dislike + 1).toString()
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
                                            val docRef3 = threadsRef3.document(id.toString())

                                            docRef3.get()
                                                .addOnSuccessListener { documentSnapshot2 ->
                                                    val dislike =
                                                        documentSnapshot2.getLong("dislike")
                                                    val like = documentSnapshot2.getLong("like")

                                                    docRef3.update("like", like!! - 1)
                                                        .addOnSuccessListener {
                                                            // Update berhasil
                                                            holder._totalThumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24_nonact)
                                                            holder._totLike.text =
                                                                (like - 1).toString()
                                                        }
                                                        .addOnFailureListener { exception ->
                                                            // Update gagal
                                                        }
                                                    docRef3.update("dislike", dislike!! + 1)
                                                        .addOnSuccessListener {
                                                            // Update berhasil
                                                            holder._totalThumbsDown.setImageResource(
                                                                R.drawable.ic_baseline_thumb_down_24
                                                            )
                                                            holder._totDis.text =
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


//                                                        holder._totalThumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                                        } else if (type == "down") {
                                            val threadsRef3 = db.collection("threads")
                                            val docRef3 = threadsRef3.document(id.toString())

                                            docRef3.get()
                                                .addOnSuccessListener { documentSnapshot2 ->
                                                    val dislike =
                                                        documentSnapshot2.getLong("dislike")
                                                    docRef3.update("dislike", dislike!! - 1)
                                                        .addOnSuccessListener {
                                                            // Update berhasil
                                                            holder._totalThumbsDown.setImageResource(
                                                                R.drawable.ic_baseline_thumb_down_24_nonact
                                                            )
                                                            holder._totDis.text =
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
                }
        }

        holder._comment.setOnClickListener {
//            onItemClickCallback.editThread(position)
//            Log.d("WOE", "PLIS")
            val colRef = db.collection("threads").whereEqualTo("judul", thread.judul).whereEqualTo("isi",thread.isi).whereEqualTo("date", thread.date)
            colRef.get()
                .addOnSuccessListener { anjer ->
                    for (anjers in anjer){
                        val id = anjers.id
                        holder._cardThread.context.startActivity(Intent(holder._cardThread.context, AddCommentActivity::class.java).apply {
                            putExtra("THREADS", id)
                        })
                    }
                }
        }
    }

    override fun getItemCount(): Int {
        return listThread!!.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}