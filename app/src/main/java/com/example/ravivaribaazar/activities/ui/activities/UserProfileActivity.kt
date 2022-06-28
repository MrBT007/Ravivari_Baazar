package com.example.ravivaribaazar.activities.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ravivaribaazar.R
import com.example.ravivaribaazar.databinding.ActivityUserProfileBinding
import com.example.ravivaribaazar.firestore.FirestoreClass
import com.example.ravivaribaazar.models.User
import com.example.ravivaribaazar.utils.Constants
import com.example.ravivaribaazar.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.iv_user_photo
import java.io.IOException

@SuppressLint("StaticFieldLeak")
private lateinit var binding:ActivityUserProfileBinding
var mUserDetails = User()
private var mSelectedImageUri: Uri? = null
private var mUserProfileImageURL: String = ""
val userHashMap = HashMap<String,Any>()

class UserProfileActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if(intent.hasExtra(Constants.EXTRA_USER_DETAILS))
        {
            mUserDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        binding.etEmailID.isEnabled = false
        binding.etEmailID.setText(mUserDetails.email)
        if(mUserDetails.profileCompleted == 0)
        {
            tv_user_profile.text = "Complete Profile"
            binding.etFirstName.isEnabled = false
            binding.etFirstName.setText(mUserDetails.firstName)

            binding.etLastName.isEnabled = false
            binding.etLastName.setText(mUserDetails.lastName)

        }
        else
        {
            setupActionBar()
            tv_user_profile.text = "Edit Profile"

            binding.etFirstName.setText(mUserDetails.firstName)

            binding.etLastName.setText(mUserDetails.lastName)

            GlideLoader(this).loadUserPicture(mUserDetails.image,iv_user_photo)

            et_mobile_number.setText(mUserDetails.mobile.toString())
            if(mUserDetails.gender == Constants.MALE)
            {
                male_radio_button.isChecked = true
            }
            else
            {
                female_radio_button.isChecked = true
            }
        }
        // we can't edit name in this stage as we already have the user's name from Register Activity (Firestore)


        iv_user_photo.setOnClickListener(this@UserProfileActivity)
        btn_submit.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.iv_user_photo->{
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
//                    showErrorSnackBar("You already have the storage permission.",false)
                    Constants.showImageChooser(this)
                }
                else
                {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        Constants.READ_STORAGE_PERMISSION_CODE)
                }
            }
            R.id.btn_submit ->{
                if(binding.etMobileNumber.text!!.isEmpty())
                {
                    showErrorSnackBar("Please enter mobile number",true)
                }
                else
                {
                    showProgressDialog(resources.getString(R.string.please_wait))

                    if(mSelectedImageUri != null)
                    {
                        FirestoreClass().uploadImageToCloudStorage(this, mSelectedImageUri)
                        Log.e("onCreate: ", mUserProfileImageURL )
                        if(mUserProfileImageURL.isNotEmpty()){
                            Log.e("onCreate if statement: ", mUserProfileImageURL )
                            userHashMap[Constants.IMAGE] = mUserProfileImageURL
                        }
                    }
                    else
                    {
                        updateUserProfileDetails()
                    }
                }
            }
        }
    }

    private fun updateUserProfileDetails()
    {
        val firstName = et_first_name.text.toString().trim{ it <= ' '}
        val lastName = et_last_name.text.toString().trim{ it <= ' '}
        if(firstName != mUserDetails.firstName)
        {
            userHashMap[Constants.FIRST_NAME] = firstName
        }
        if(lastName != mUserDetails.lastName)
        {
            userHashMap[Constants.LAST_NAME] = lastName
        }
        val mobileNumber = et_mobile_number.text.toString().trim{it <= ' '}
        val gender = if(female_radio_button.isChecked)
        {
            Constants.FEMALE
        }
        else{
            Constants.MALE
        }

        if(mUserProfileImageURL.isNotEmpty())
        {
            userHashMap[Constants.IMAGE] = mUserProfileImageURL
        }
        userHashMap[Constants.MOBILE] = mobileNumber.toLong()
        userHashMap[Constants.GENDER] = gender
        userHashMap[Constants.COMPLETE_PROFILE] = 1
//        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().updateUserDetails(this, userHashMap)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constants.READ_STORAGE_PERMISSION_CODE)
        {
            //If permission is granted
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
//                showErrorSnackBar("The storage permission is granted",false)
                Constants.showImageChooser(this)
            }
            else
            {
                Toast.makeText(this, R.string.read_permission_storage_denied, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == Constants.PICK_IMAGE_REQUEST_CODE)
            {
                if(data!=null)
                {
                    try {
                        mSelectedImageUri = data.data!!
//                        iv_user_photo.setImageURI(selectedFileImageFileUri)
                        GlideLoader(this).loadUserPicture(mSelectedImageUri!!,iv_user_photo)
                    }
                    catch (e:IOException)
                    {
                        e.printStackTrace()
                        Toast.makeText(this, "Image Selection Failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    fun userProfileUpdateSuccess()
    {
        hideProgressDialog()
        Toast.makeText(this, "Your details are updated", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    fun imageUploadSuccess(imageRL:String)
    {
//        hideProgressDialog()
        mUserProfileImageURL = imageRL

        // as in any case we have to update the user details although we haven't selected a profile image.
        updateUserProfileDetails()
        Log.e("imageUploadSuccess: ", mUserProfileImageURL)
    }

    private fun setupActionBar()
    {
        setSupportActionBar(toolbar_user_profile)

        val actionbar = supportActionBar
        if(actionbar != null)
        {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setHomeAsUpIndicator(R.drawable.ic_back_arrow)
        }
        toolbar_user_profile.setNavigationOnClickListener{ onBackPressed()}
    }

}