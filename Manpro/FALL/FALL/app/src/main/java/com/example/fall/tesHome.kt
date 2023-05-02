package com.example.fall

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fall.databinding.ActivityTesHomeBinding
import com.example.fall.ui.home.HomeFragment

class tesHome : AppCompatActivity() {

    private lateinit var binding: ActivityTesHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("CEK TES HOME", "KESINI")
        binding = ActivityTesHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_tes_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        val dataBundle: ArrayList<thread>? = intent.getParcelableArrayListExtra("DATA")
        if (dataBundle != null) {
            Log.d("CEK TES HOME DATA", "data: $dataBundle")
        }

        val bundle = Bundle()
        bundle.putParcelableArrayList("DATA", dataBundle)

        val fragment = HomeFragment()
        fragment.arguments = bundle

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}