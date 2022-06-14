package com.example.ravivaribaazar.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ravivaribaazar.R
import com.example.ravivaribaazar.databinding.ActivityUserProfileBinding
import com.example.ravivaribaazar.models.User
import com.example.ravivaribaazar.utils.Constants

@SuppressLint("StaticFieldLeak")
private lateinit var binding:ActivityUserProfileBinding
class UserProfileActivity : baseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var userDetails = User()
        if(intent.hasExtra(Constants.EXTRA_USER_DETAILS))
        {
            userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }
        // we can't edit name in this stage as we already have the user's name from Register Activity (Firestore)
        binding.etFirstName.isEnabled = false
        binding.etFirstName.setText(userDetails.firstName)

        binding.etLastName.isEnabled = false
        binding.etLastName.setText(userDetails.lastName)

        binding.etEmailID.isEnabled = false
        binding.etEmailID.setText(userDetails.email)

        // TODO: set mobile number, gender, profileCompleted to firebase 
        binding.btnSubmint.setOnClickListener{
            if(binding.etMobileNumber.text!!.isEmpty())
            {
                showErrorSnackBar("Please enter mobile number",true)
            }
            else
            {
                showErrorSnackBar("Your Number is ${binding.etMobileNumber.text}",false)
            }
        }

    }
}