package com.example.fall.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.fall.*
import com.example.fall.databinding.FragmentHomeBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

private const val ARG_PARAM1 = "DATA"

class HomeFragment : Fragment() {
    private lateinit var _judul: Array<String>
    private lateinit var _isi : Array<String>

    private var _binding: FragmentHomeBinding? = null
    private var param1: ArrayList<thread>? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelableArrayList(ARG_PARAM1)

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnAddThread = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        val _loading = view.findViewById<LottieAnimationView>(R.id.loading_animation)

        btnAddThread.setOnClickListener {
            val intent = Intent(view.context, MainActivityFragment::class.java)
            startActivity(intent)
        }

        // AMBIL DATA ARRAY LIST DARI DB DAN MASUKKAN KE RECYCLE VIEW
        val db = FirebaseFirestore.getInstance()
        val threadsRef = db.collection("threads").whereEqualTo("hirarki", "").orderBy("date", Query.Direction.DESCENDING)
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

                    Log.d("CEK DATA HOME", "Data: $dataBundle")
                    val rvThread = view.findViewById<RecyclerView>(R.id.recyclerView)
                    rvThread.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
                    val adapterRV = adapterthread(dataBundle)
                    rvThread.adapter = adapterRV

                }

                _loading.visibility = View.INVISIBLE
            }
            .addOnFailureListener { exception ->
                // Handle error
            }


//        if (arguments != null){
//            dataBundle = arguments?.getParcelableArrayList<thread>("DATA") as ArrayList<thread>
//            Log.d("DATA_BUNDLE", "data: $dataBundle")
//        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}