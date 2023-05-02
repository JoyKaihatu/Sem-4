package com.example.fall.ui.notifications

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fall.ChangePasswordActivity
import com.example.fall.R
import com.example.fall.databinding.FragmentNotificationsBinding
import com.google.firebase.firestore.FirebaseFirestore

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textNotifications
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etNn = view.findViewById<EditText>(R.id.editTextNickName)
        val etSex = view.findViewById<EditText>(R.id.editTextSex)
//        etSex.setOnKeyListener(null)
        val etUsr = view.findViewById<EditText>(R.id.editTextUsrNm)
        val etAge = view.findViewById<EditText>(R.id.editTextNumberAge)
        val tvChangePass = view.findViewById<TextView>(R.id.textViewPass)
        val _btnSave = view.findViewById<Button>(R.id.buttonSave)

        val sharedPreferences = view.context.getSharedPreferences("SessionUser", Context.MODE_PRIVATE)
        val savedName = sharedPreferences.getString("id_user", "")

        val db = FirebaseFirestore.getInstance()

        val col = db.collection("account").document(savedName.toString())

        col.get().addOnSuccessListener { anjer ->
            val age = anjer.get("age")
            val nn = anjer.getString("nickname")
            val pass = anjer.getString("password")
            val sex = anjer.getString("sex")
            val username = anjer.getString("username")

            etNn.setText(nn)
            etSex.setText(sex)
            etUsr.setText(username.toString())
            etAge.setText(age.toString())
        }

        _btnSave.setOnClickListener {
            val etNnNw = view.findViewById<EditText>(R.id.editTextNickName)
            val etUsrNw = view.findViewById<EditText>(R.id.editTextUsrNm)

            val col2 = db.collection("account").document(savedName.toString())
            col2.update("nickname", etNnNw.text.toString(), "username", etUsrNw.text.toString().lowercase())
                .addOnSuccessListener {
                    Toast.makeText(
                        view.context,
                        "Success!",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }

        tvChangePass.setOnClickListener {
            val intent = Intent(view.context, ChangePasswordActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}