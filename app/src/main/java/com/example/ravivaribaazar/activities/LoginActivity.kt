package com.example.ravivaribaazar.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.ravivaribaazar.R
import com.example.ravivaribaazar.databinding.ActivityLoginBinding
import com.example.ravivaribaazar.databinding.ActivitySplashBinding
import org.w3c.dom.Text
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.ThemedSpinnerAdapter
import com.example.ravivaribaazar.MainActivity
import com.example.ravivaribaazar.firestore.FirestoreClass
import com.example.ravivaribaazar.models.User
import com.example.ravivaribaazar.utils.Constants
import com.example.ravivaribaazar.utils.RBButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.grpc.LoadBalancer

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityLoginBinding

class LoginActivity : baseActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide() // hide action bar

        // hide status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }



        val attrib = window.attributes
        attrib.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES

        var logintoregister: TextView = findViewById(R.id.logintoregister_register)
        var loginButton: Button = findViewById(R.id.loginButton)
        var forgotpassword: TextView = findViewById(R.id.forgot_password)
        logintoregister.setOnClickListener()
        {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        loginButton.setOnClickListener {
//            Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show()
            logInRegisteredUser()
        }

        forgotpassword.setOnClickListener{
            startActivity(Intent(this,ForgotPassword::class.java))
        }
//        binding.logintoregisterRegister.setOnClickListener()
//        {
//            startActivity(Intent(this,RegisterActivity::class.java))
//        }

    }

    private fun validateLoginDetails(): Boolean
    {
        var etEmailLogin: EditText = findViewById(R.id.et_emailLogin)
        var etPasswordLogin: EditText = findViewById(R.id.et_passwordLogin)
        return when {
            TextUtils.isEmpty(etEmailLogin.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(etPasswordLogin.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
//                showErrorSnackBar("Your details are valid.", false)
                true
            }
        }
    }

    private fun logInRegisteredUser() {
        if (validateLoginDetails()) {
            showProgressDialog(resources.getString(R.string.please_wait))
            var etEmailLogin: EditText = findViewById(R.id.et_emailLogin)
            var etPasswordLogin: EditText = findViewById(R.id.et_passwordLogin)
            val email = etEmailLogin.text.toString().trim { it <= ' ' }
            val password = etPasswordLogin.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        FirestoreClass().getUserDetails(this@LoginActivity)
                    } else {
                        hideProgressDialog()
                        showErrorSnackBar("Incorrect Email ID or Password", true)
                    }
                }
        }
    }

    fun userLoggedInSuccess(user: User)
    {
        hideProgressDialog()

        Log.i("First Name:",user.firstName)
        Log.i("Last Name:",user.lastName)
        Log.i("Email:",user.email)

        if(user.profileCompleted == 0)
        {
            val intent = Intent(this,UserProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS,user)
            startActivity(intent)
        }
        else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
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
