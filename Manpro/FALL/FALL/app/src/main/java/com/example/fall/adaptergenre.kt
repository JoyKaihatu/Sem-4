package com.example.fall.ui.explore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fall.*
import com.google.firebase.firestore.FirebaseFirestore

class adaptergenre(private val listGenre: ArrayList<genre>?) : RecyclerView.Adapter<adaptergenre.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _namaGenre: TextView = itemView.findViewById(R.id.textViewGenre)
        var _cardGenre: CardView = itemView.findViewById(R.id.cardClick)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback
    interface OnItemClickCallback{
        fun editThread(pos: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.genreitem, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var genre = listGenre!!.get(position)
        val db = FirebaseFirestore.getInstance()
        var id_genre = ""
        holder._namaGenre.setText(genre.namaGenre)

        Log.d("CEK SIZE ADAPTER", "DATA: $listGenre")

        holder._cardGenre.setOnClickListener{

            val colGen = db.collection("threads").whereEqualTo("id_genre", genre.namaGenre)
            colGen.get()
                .addOnSuccessListener { anjer ->
                    for (anjers in anjer){
                        id_genre = anjers.getString("id_genre").toString()
//                        holder._cardGenre.context.startActivity(Intent(holder._cardThread.context, CommentActivity::class.java).apply {
//                            putExtra("THREADS", id)
//                        })
                            Log.d("ID ANJERS", id_genre)
                        onItemClickCallback.editThread(id_genre)

                    }
                }
//            val mBundle = Bundle()
//            mBundle.putString("POS", position.toString())
//
//            val mfExplore = com.example.fall.ui.explore.ExploreFragment()
//            mfExplore.arguments = mBundle
//
//            val mFragmentManager =
//            val bundle = Bundle()
//            bundle.putString("POS", id_genre)
//
//            val fragment = com.example.fall.ui.explore.ExploreFragment()
//            fragment.arguments = bundle
//
//            val navController = holder.itemView.findNavController()
//            navController.navigate(R.id.navigation_dashboard, bundle)

        }
    }

    override fun getItemCount(): Int {
        return listGenre!!.size
    }

    fun setOnItemClickListener(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


}
