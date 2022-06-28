package com.example.ravivaribaazar

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import com.example.ravivaribaazar.databinding.ActivityMainBinding
import com.example.ravivaribaazar.utils.Constants

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    private lateinit var binding:ActivityMainBinding
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val sharedPreferences = getSharedPreferences(Constants.RB_PREFERENCES,Context.MODE_PRIVATE)
        val username = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME,"")!!
        binding.tvMain.text = "Hello $username."

    }
}