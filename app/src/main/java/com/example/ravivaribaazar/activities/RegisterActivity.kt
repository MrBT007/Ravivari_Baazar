package com.example.ravivaribaazar.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.ravivaribaazar.R
import com.example.ravivaribaazar.databinding.ActivityRegisterBinding
import com.example.ravivaribaazar.firestore.FirestoreClass
import com.example.ravivaribaazar.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

private lateinit var binding: ActivityRegisterBinding

class RegisterActivity : baseActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

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
        setupActionBar()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var registertologin: TextView = findViewById(R.id.logintoregister_register)

        registertologin.setOnClickListener()
        {
            onBackPressed()
        }

        binding.RegisterButton.setOnClickListener {
//            Toast.makeText(this,"Button clicked",Toast.LENGTH_SHORT).show()
//            validateRegisterDetails()
              registerUser()
        }
    }

    private fun setupActionBar() {
//        setSupportActionBar(binding.toolbarRegisterActivity)
        val actionBar = supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        }
//        binding.toolbarRegisterActivity = actionBar
        setSupportActionBar(binding.toolbarRegisterActivity)
        binding.toolbarRegisterActivity.setNavigationOnClickListener { onBackPressed() }
    }


    private fun validateRegisterDetails(): Boolean {
        return when
        {
            TextUtils.isEmpty(binding.etFirstName.text.toString().trim{it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name),true)
                false
            }
            TextUtils.isEmpty(binding.etLastName.text.toString().trim{it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name),true)
                false
            }
            TextUtils.isEmpty(binding.etEmailID.text.toString().trim{it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
                false
            }
            TextUtils.isEmpty(binding.etPassword.text.toString().trim{it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password),true)
                false
            }
            TextUtils.isEmpty(binding.etConfirmPassword.text.toString().trim{it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password),true)
                false
            }
            binding.etPassword.text.toString().trim{it <= ' '} != binding.etConfirmPassword.text.toString()
                .trim{it <= ' '} -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),true)
                false
            }
            !binding.termsAndCondition.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_agree_terms_and_condition),true)
                false
            }
            else -> {
//                showErrorSnackBar(resources.getString(R.string.register_successfull),false)
                true
            }
        }
    }

    private fun registerUser()
    {
        // check if entries are valid or not
        if(validateRegisterDetails())
        {
            showProgressDialog(resources.getString(R.string.please_wait))

            val email = binding.etEmailID.text.toString().trim{it <= ' '}
            val password = binding.etPassword.text.toString().trim{it <= ' '}

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener { task ->
                        if(task.isSuccessful)
                        {
                            val firebaseUser = task.result!!.user!!
                            val user = User(
                                firebaseUser.uid,
                                binding.etFirstName.text.toString().trim { it <= ' ' },
                                binding.etLastName.text.toString().trim { it <= ' ' },
                                email,
                                password)
//                            Log.d(TAG, "registerUser: ${firebaseUser.uid}" )

                            FirestoreClass().registerUser(this,user)
//                            showErrorSnackBar("You are registered successfully\nYour id is ${firebaseUser.uid}",false)

//                            FirebaseAuth.getInstance().signOut()
//                            finish()
                        }
                        else
                        {
                            hideProgressDialog()
//                            Log.w(TAG, "registerUser: fail",task.exception)
                            showErrorSnackBar(task.exception?.message.toString(),true)
//                            Toast.makeText(this,"Authentication failed",Toast.LENGTH_SHORT).show()
                        }
                    }
                )
        }
    }
    fun userRegisteredSuccessfully()
    {
        hideProgressDialog()
        Toast.makeText(this, "You are Registered Successfully", Toast.LENGTH_SHORT).show()
    }
}


