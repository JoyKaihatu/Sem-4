package com.example.fall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.fall.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.checkerframework.common.subtyping.qual.Bottom

class MainActivityFragment : AppCompatActivity() {
    val fragmentThread : Fragment = ThreadFragment()
    val fragmentExplore : Fragment = ExploreFragment()
    val mFragmentManager : FragmentManager = supportFragmentManager
    val activeException : Fragment = fragmentThread

    private lateinit var menu : Menu
    private lateinit var menuItem : MenuItem
    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_fragment)

        Log.d("KE MAINA FRAG", "KE MAINA FRAG")


        val mfHome = NewThreadFragment()

        mFragmentManager.findFragmentByTag(NewThreadFragment::class.java.simpleName)
        mFragmentManager
            .beginTransaction()
            .add(R.id.frameContainer, mfHome, NewThreadFragment::class.java.simpleName)
            .commit()
    }
}