package com.example.fall

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.os.CountDownTimer
import android.telephony.SmsManager
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class OtpActivity : AppCompatActivity() {

    private lateinit var timerTextView: TextView
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        // DB INIT
        db = FirebaseFirestore.getInstance()

        val btnToLogin = findViewById<Button>(R.id.buttonToLogin)
        val etCd1 = findViewById<EditText>(R.id.editTextNumber1)
        val etCd2 = findViewById<EditText>(R.id.editTextNumber2)
        val etCd3 = findViewById<EditText>(R.id.editTextNumber3)
        val etCd4 = findViewById<EditText>(R.id.editTextNumber4)
        val etCd5 = findViewById<EditText>(R.id.editTextNumber5)
        val etCd6 = findViewById<EditText>(R.id.editTextNumber6)
        timerTextView = findViewById(R.id.textViewTimer)

        val getOtp = intent.getStringExtra("OTP")
        val user = intent.getStringExtra("user")
        val pass = intent.getStringExtra("pass")
        val age = intent.getStringExtra("age")
        val nick = intent.getStringExtra("nick")
        val sex = intent.getStringExtra("sex")
        val nohp = intent.getStringExtra("noHp")

        val timer = object: CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val time = millisUntilFinished / 1000
                timerTextView.text = "$time second remaining"
            }

            override fun onFinish() {
                timerTextView.setText("Waktu Habis!!!")
                etCd1.setText("")
                etCd2.setText("")
                etCd3.setText("")
                etCd4.setText("")
                etCd5.setText("")
                etCd6.setText("")

                val builder = AlertDialog.Builder(this@OtpActivity)
                builder.setTitle("Apakah anda tidak mendapat pesan kode OTP?")
                builder.setMessage("Kirimkan kode OTP lagi!")
                builder.setPositiveButton("Ok") { dialog, which ->
                    val otp = generateOTP(nohp.toString())
                    Log.d("OTP", otp)
                    //TAMBAHKAN PENGECEKAN HASIL RUNDOM KALO SUDAH PERNAH MAKA AKAN DI PANGGIL ULANG generateOTP()
                    val MY_PERMISSIONS_REQUEST_SEND_SMS = 123
                    if (ContextCompat.checkSelfPermission(this@OtpActivity, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                        // Belum mendapat izin
                        ActivityCompat.requestPermissions(this@OtpActivity,
                            arrayOf(Manifest.permission.SEND_SMS),
                            MY_PERMISSIONS_REQUEST_SEND_SMS)
                    } else {
                        // Telah mendapat izin
                        // Lakukan proses mengirim SMS di sini
                        val smsManager = SmsManager.getDefault()
                        val phoneNumber = intent.getStringExtra("noHp") // ganti dengan nomor telepon Anda
                        val message = "[FALL] Kode OTP anda adalah $otp, mohon untuk tidak memberitahukan kepada siapapun!!!" // ganti dengan kode OTP yang ingin Anda kirim
                        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
                        startActivity(Intent(this@OtpActivity, OtpActivity::class.java).apply {
                            putExtra("OTP", otp)
                            putExtra("noHp", phoneNumber)
                        })
                    }
                }
                builder.setNegativeButton("Cancel") { dialog, which ->
                    Toast.makeText(this@OtpActivity, "Kode OTP gagal terkirim!", Toast.LENGTH_SHORT).show()
                }
                val dialog = builder.create()
                dialog.show()
            }
        }
        timer.start()



        btnToLogin.setOnClickListener {
            if (getOtp != null) {
                if (etCd1.text.toString() == getOtp.get(0).toString() && etCd2.text.toString() == getOtp.get(1).toString() &&
                    etCd3.text.toString() == getOtp.get(2).toString() && etCd4.text.toString() == getOtp.get(3).toString() &&
                    etCd5.text.toString() == getOtp.get(4).toString() && etCd6.text.toString() == getOtp.get(5).toString()){
                    TambahData(nick.toString(), user.toString(), pass.toString(), age!!.toInt(), sex.toString(), nohp.toString(), getOtp)
                    startActivity(Intent(this@OtpActivity, Login::class.java))
                }
                else{
                    Toast.makeText(this, "KODE OTP SALAH!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun generateOTP(noHp: String): String {
        val random = Random()
        val num = random.nextInt(900000) + 100000
        val query = db.collection("otp").whereEqualTo("noHp", noHp)
        query.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val otp = document.getString("otp")
                    // do something with the otp

                    if (num.toString() == otp){
                        generateOTP(noHp)
                    }
                }
            }


        return num.toString()
    }

    private fun TambahData(nickname: String, username: String, password: String, age: Int, sex: String, nohp: String, otp: String) {
        val user = hashMapOf(
            "nickname" to nickname,
            "username" to username,
            "password" to password,
            "age" to age,
            "sex" to sex,
            "nohp" to nohp
        )

        val otp1 = hashMapOf(
            "noHp" to nohp,
            "otp" to otp
        )

        db.collection("account").add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("CEK REGISTER", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("CEK REGISTER", "Error adding document", e)
            }

        db.collection("otp").add(otp1)
    }

}