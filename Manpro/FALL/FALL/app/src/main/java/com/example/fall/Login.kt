package com.example.fall

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.fall.ui.home.HomeFragment
import com.google.firebase.firestore.FirebaseFirestore

class Login : AppCompatActivity() {
    lateinit var db: FirebaseFirestore

    private var _userN : MutableList<String> = emptyList<String>().toMutableList()
    private var _pass : MutableList<String> = emptyList<String>().toMutableList()
    private var _id : MutableList<String> = emptyList<String>().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val txtRegist = findViewById<TextView>(R.id.txtSignUp)
        val _loginUsr = findViewById<EditText>(R.id.loginUsr)
        val _loginPass = findViewById<EditText>(R.id.regPass)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)


        db = FirebaseFirestore.getInstance()
        val sharedPreferences = getSharedPreferences("SessionUser", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val threadsRef = db.collection("threads")


        var dbAccount = db.collection("account")

        dbAccount.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    _userN.add(document.data["username"].toString())
                    _pass.add(document.data["password"].toString())
                    _id.add(document.id.toString())
                    Log.d("CEK DATA", "CEK ${_userN}")
                    Log.d("GET DATA", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("GET DATA", "Error getting documents: ", exception)
            }

        txtRegist.setOnClickListener {
            val toRegistIntent = Intent(this@Login, Registration::class.java)
            startActivity(toRegistIntent)
        }

        buttonLogin.setOnClickListener{
            var cek = false
            for (x in 0.._userN.size-1){
                if (_loginUsr.text.toString().equals(_userN[x].toString()) and _loginPass.text.toString().equals(_pass[x].toString())){
                    cek = true
                    Toast.makeText(
                        this@Login,
                        "Login Success!",
                        Toast.LENGTH_SHORT
                    ).show()

                    editor.putString("id_user", _id[x].toString())
                    editor.apply()
// AMBIL DATA ARRAY LIST DARI DB DAN MASUKKAN KE RECYCLE VIEW

                    var dataBundle = ArrayList<thread>()


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

                            }
                            Log.d("CEK BUNDLE LOGIN1", "data: $dataBundle")



                            // PERLU DIGANTI!!
                            val eIntent = Intent(this@Login, tesHome::class.java).apply {
                                putExtra("DATA", dataBundle)
                            }
                            startActivity(eIntent)

                        }
                        .addOnFailureListener { exception ->
                            // Handle error
                        }

                    Log.d("CEK BUNDLE LOGIN", "data: $dataBundle")




                }
            }

            if (!cek){
                Toast.makeText(
                    this@Login,
                    "Email atau Password Salah!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}