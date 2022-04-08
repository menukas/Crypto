package com.amandrykin.crypto

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity: AppCompatActivity() {

    private lateinit var navController: NavController
    private var navHostFragment: NavHostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.mainNavHostFragment) as? NavHostFragment ?: return

        navController = findNavController(R.id.mainNavHostFragment)

        val mainBottomNavView = findViewById<BottomNavigationView>(R.id.mainBottomNavView)
        mainBottomNavView.setupWithNavController(navController)
    }
}