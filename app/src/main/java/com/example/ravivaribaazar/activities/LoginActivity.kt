package com.example.ravivaribaazar.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.example.ravivaribaazar.R
import com.example.ravivaribaazar.databinding.ActivityLoginBinding
import com.example.ravivaribaazar.databinding.ActivitySplashBinding
import org.w3c.dom.Text
import android.widget.Toast




class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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
        binding = ActivityLoginBinding.inflate(layoutInflater)
        var logintoregister:TextView = findViewById(R.id.logintoregister_register)
        logintoregister.setOnClickListener()
        {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
//        binding.logintoregisterRegister.setOnClickListener()
//        {
//            startActivity(Intent(this,RegisterActivity::class.java))
//        }

    }
    private var pressedTime = 0L
    override fun onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            finish()
        } else {
            Toast.makeText(baseContext, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
        pressedTime = System.currentTimeMillis()
    }
}
