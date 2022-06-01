package com.example.ravivaribaazar.activities

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.ravivaribaazar.R
import com.google.android.material.snackbar.Snackbar

open class baseActivity : AppCompatActivity() {

    private lateinit var mProgressDialog: Dialog

    @SuppressLint("ShowToast")
    fun showErrorSnackBar(message: String, error: Boolean) {
        val snackbar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view

        if (error) {
            snackbarView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorSnackBarError
                )
            )
        } else {
            snackbarView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorSnackBarSuccess
                )
            )
        }
        snackbar.show()
    }

    fun showProgressDialog(text:String)
    {
        mProgressDialog = Dialog(this)
        /*Set the screen content fromalayout resource.
        The resource will be inflated,adding all top-level views to the screen.*/
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.findViewById<TextView>(R.id.tv_progress_text).text = text
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        // Start the dialog and display it on screen.
        mProgressDialog.show()
    }

    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }
}