package com.k5.omsetku

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.k5.omsetku.fragment.CategoryFragment
import com.k5.omsetku.fragment.HomeFragment
import com.k5.omsetku.fragment.ProductFragment
import com.k5.omsetku.fragment.SalesFragment
import androidx.core.graphics.toColorInt

class MainActivity : AppCompatActivity() {
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        window.statusBarColor = "#205072".toColorInt()
        window.navigationBarColor = "#205072".toColorInt()
        window.decorView.systemUiVisibility = 0

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadFragment(HomeFragment())

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNav.setOnItemSelectedListener {
            item -> when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_category -> loadFragment(CategoryFragment())
                R.id.nav_product -> loadFragment(ProductFragment())
                R.id.nav_sales -> loadFragment(SalesFragment())
            }
            true
        }
    }

    @SuppressLint("CommitTransaction")
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.host_fragment, fragment)
            .commit()
    }
}