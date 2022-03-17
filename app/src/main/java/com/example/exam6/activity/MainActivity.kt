package com.example.exam6.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.exam6.R
import com.example.exam6.fragment.PhysicalFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fl_Fragment: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = PhysicalFragment()
        val searchFragment = PhysicalFragment()
        val messagesFragment = PhysicalFragment()
        val profileFragment = PhysicalFragment()
        val cardsFragment = PhysicalFragment()
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        fl_Fragment = findViewById(R.id.fl_Fragment)

        replaceFragment(cardsFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> replaceFragment(homeFragment)
                R.id.menu_chat -> replaceFragment(searchFragment)
                R.id.menu_menu -> replaceFragment(cardsFragment)
                R.id.menu_arrows -> replaceFragment(profileFragment)
                R.id.menu_user -> replaceFragment(messagesFragment)

            }
            true
        }

    }

    fun replaceFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name
        val manager = supportFragmentManager
        val fragmentPopped = manager.popBackStackImmediate(backStateName, 0)
        if (!fragmentPopped) {
            val ft = manager.beginTransaction()
            ft.replace(R.id.fl_Fragment, fragment)
            ft.addToBackStack(backStateName)
            ft.commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1)
            finish()
        else
            super.onBackPressed()
    }
}