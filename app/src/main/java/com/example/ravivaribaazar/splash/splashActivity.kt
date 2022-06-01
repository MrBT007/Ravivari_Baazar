package com.example.ravivaribaazar.splash

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import com.example.ravivaribaazar.MainActivity
import com.example.ravivaribaazar.R
import com.example.ravivaribaazar.activities.LoginActivity
import com.example.ravivaribaazar.databinding.ActivitySplashBinding

class splashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide() // hide action bar

        // hide status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }
        else
        {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        //splash time
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            startActivity((Intent(this@splashActivity,LoginActivity::class.java)))
        },2500)

//       we have created a util file for a text view to apply a font (RBTextViewBold.kt)
//        val typeface = Typeface.createFromAsset(assets,"Montserrat-Bold.ttf")
//        binding.tvAppName.typeface

    }
}