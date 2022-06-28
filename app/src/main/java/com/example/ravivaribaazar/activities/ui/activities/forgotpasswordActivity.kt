package com.example.ravivaribaazar.activities.ui.activities

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.ravivaribaazar.R
import com.example.ravivaribaazar.databinding.ActivityForgotpasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : BaseActivity()
{
    private lateinit var binding: ActivityForgotpasswordBinding
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        binding = ActivityForgotpasswordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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

        binding.submitButton.setOnClickListener{
            val email = binding.etEmailLoginResetActivity.text.toString().trim{it <= ' '}
            if(email.isEmpty())
            {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
            }
            else
            {
                showProgressDialog(resources.getString(R.string.please_wait))
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{task ->
                        hideProgressDialog()
                        if(task.isSuccessful)
                        {
                            Toast.makeText(this, "Email sent successfully to reset your password", Toast.LENGTH_LONG).show()
                            finish()
                        }
                    }
            }
        }
    }
}