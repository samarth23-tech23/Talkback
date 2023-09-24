package com.example.talkback

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.commit
import com.example.talkback.databinding.ActivityMainScreenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainScreen : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNav.setOnNavigationItemSelectedListener(this)

        // Call onHomeClicked to show the RunningFragment when the activity is created
        onHomeClicked()
    }

    private fun onHomeClicked() {
        supportFragmentManager.commit {
            replace(R.id.frame_content, HomeFragment())
        }
    }

    private fun onHistoryClicked() {
        // Implement your code for handling History here
    }

    private fun onProfileClicked() {
        // Implement your code for handling Profile here
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_home) {
            onHomeClicked()
            return true
        } else if (item.itemId == R.id.nav_history) {
            onHistoryClicked()
            return true
        } else if (item.itemId == R.id.nav_profile) {
            onProfileClicked()
            return true
        } else return false
    }
}
