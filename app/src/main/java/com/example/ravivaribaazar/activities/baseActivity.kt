package com.example.ravivaribaazar.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.ravivaribaazar.R
import com.google.android.material.snackbar.Snackbar

open class baseActivity : AppCompatActivity() {

    @SuppressLint("ShowToast")
    fun showErrorSnackBar(message: String, error: Boolean)
    {
        val snackbar =
            Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view

        if(error)
        {
            snackbarView.setBackgroundColor(ContextCompat.getColor(this,R.color.colorSnackBarError))
        }
        else
        {
            snackbarView.setBackgroundColor(ContextCompat.getColor(this,R.color.colorSnackBarSuccess))
        }
        snackbar.show()
    }
}