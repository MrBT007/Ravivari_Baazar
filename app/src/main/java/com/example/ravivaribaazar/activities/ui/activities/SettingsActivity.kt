package com.example.ravivaribaazar.activities.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ravivaribaazar.R
import com.example.ravivaribaazar.firestore.FirestoreClass
import com.example.ravivaribaazar.models.User
import com.example.ravivaribaazar.utils.Constants
import com.example.ravivaribaazar.utils.GlideLoader
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_settings.iv_user_photo
import kotlinx.android.synthetic.main.activity_user_profile.*


class SettingsActivity : BaseActivity() {
    private lateinit var mUserDetails:User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setupActionBar()

        btn_logout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        tv_edit.setOnClickListener{
            val intent = Intent(this,UserProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS,mUserDetails)
            startActivity(intent)
        }

        iv_user_photo.setOnClickListener{
            startActivity(Intent(this,ProfilePreviewActivity::class.java))
        }
    }

    private fun setupActionBar()
    {
        setSupportActionBar(toolbar_settings_activity)

        val actionbar = supportActionBar
        if(actionbar != null)
        {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setHomeAsUpIndicator(R.drawable.ic_back_arrow)
        }
        toolbar_settings_activity.setNavigationOnClickListener{ onBackPressed()}
    }

    private fun getUserDetails()
    {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getUserDetails(this)
    }

    @SuppressLint("SetTextI18n")
    fun userDetailsSuccess(user: User)
    {
        mUserDetails = user
        hideProgressDialog()
        GlideLoader(this).loadUserPicture(user.image,iv_user_photo)
        tv_name.text = "${user.firstName} ${user.lastName}"
        tv_gender.text = user.gender
        tv_mobile_number.text = "${user.mobile}"
        tv_email.text = user.email
    }

    // after minimizing the app, it calls onPause method, and when we return to the app it calls onResume
    // so we just don't want to loose the data, instead we save it
    override fun onResume() {
        super.onResume()
        getUserDetails()
    }
}