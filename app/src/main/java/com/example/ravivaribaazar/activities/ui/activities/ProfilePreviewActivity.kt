package com.example.ravivaribaazar.activities.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.ravivaribaazar.R
import com.example.ravivaribaazar.firestore.FirestoreClass
import com.example.ravivaribaazar.models.User
import com.example.ravivaribaazar.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_profile_preview.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.iv_user_photo
import kotlinx.android.synthetic.main.sliding_drawer.*

class ProfilePreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_preview)
        setupActionBar()
        getUserImage()
    }

    private fun setupActionBar()
    {
        setSupportActionBar(toolbar_profile_photo_activity)

        val actionbar = supportActionBar
        if(actionbar != null)
        {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setHomeAsUpIndicator(R.drawable.ic_back_arrow)
        }
        toolbar_profile_photo_activity.setNavigationOnClickListener{ onBackPressed()}
    }

    private fun getUserImage()
    {
        FirestoreClass().getUserDetails(this)
    }

    fun userDetailsSuccess(user: User)
    {
        GlideLoader(this).loadUserPicture(user.image,fullScreen_profile_image_view)
    }

}