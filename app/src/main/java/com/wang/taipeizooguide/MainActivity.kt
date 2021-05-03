package com.wang.taipeizooguide

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val bottomNavIds = setOf(
        R.id.navigation_zoo, R.id.navigation_arboretum, R.id.navigation_about
    )

    private val appBarConfiguration = AppBarConfiguration(bottomNavIds)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navController.addOnDestinationChangedListener { _, destination, _ ->
            handleActionBar(destination.id in bottomNavIds)
            handleBottomNav(destination.id in bottomNavIds)
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    private fun handleActionBar(shouldHideSubtitle: Boolean) {
        supportActionBar?.subtitle = if (shouldHideSubtitle) "" else supportActionBar?.subtitle
    }

    private fun handleBottomNav(shouldShowBottomNav: Boolean) {
        nav_view.visibility = if (shouldShowBottomNav) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }

}