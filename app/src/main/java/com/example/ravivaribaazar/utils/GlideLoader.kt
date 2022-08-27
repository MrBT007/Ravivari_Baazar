package com.example.ravivaribaazar.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.ravivaribaazar.R
import java.io.IOException

class GlideLoader(val context: Context)
{
    fun loadUserPicture(imageUri: Any,imageView: ImageView)
    {
        try {
            Glide
                .with(context)
                .load(imageUri)
                .centerCrop()
                .placeholder(R.drawable.user_profile_default)
                .into(imageView)
        }
        catch (e:IOException)
        {
            e.printStackTrace()
        }
    }

    fun loadProductPicture(imageUri: Any,imageView: ImageView)
    {
        try {
            Glide
                .with(context)
                .load(imageUri)
                .centerCrop()
                .into(imageView)
        }
        catch (e:IOException)
        {
            e.printStackTrace()
        }
    }
}