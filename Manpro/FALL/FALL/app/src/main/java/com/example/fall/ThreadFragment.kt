package com.example.fall

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
private const val ARG_PARAM1 = "DATA"
private const val ARG_PARAM2 = "param2"

class ThreadFragment : Fragment() {
    private var param1: ArrayList<thread>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelableArrayList(ARG_PARAM1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("KESINI?", "THREAF FRAGMENT")
        val btnAddThread = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        btnAddThread.setOnClickListener{
            val mfExp = NewThreadFragment()
            val mFragment = parentFragmentManager
            mFragment.beginTransaction().apply {
                replace(R.id.frameContainer, mfExp, NewThreadFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

//        var dataBundle = ArrayList<thread>()
//
//        if (arguments != null){
//            dataBundle = arguments?.getParcelableArrayList<thread>(ARG_PARAM1) as ArrayList<thread>
//            Log.d("DATA_BUNDLE", "data: $dataBundle")
//        }
//
//
//        val rvThread = view.findViewById<RecyclerView>(R.id.recyclerView)
//        rvThread.layoutManager = LinearLayoutManager(view.context)
//        val adapterRV = adapterthread(dataBundle)
//        rvThread.adapter = adapterRV
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thread, container, false)
    }

}