package com.example.ravivaribaazar.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.ravivaribaazar.activities.LoginActivity
import com.example.ravivaribaazar.activities.RegisterActivity
import com.example.ravivaribaazar.models.User
import com.example.ravivaribaazar.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass
{
    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity,userInfo:User)
    {
        // "Users" is the collection, if it is already created then it will not create the same one again
        mFireStore.collection(Constants.USERS)
//          Document ID is for user fields, here Document is User ID
            .document(userInfo.id)
//          userInfo is Field and SetOptions is set to merge. It is for if we wants to merge later on instead of replacing the fields.
            .set(userInfo, SetOptions.merge())
            .addOnCompleteListener{
                activity.userRegisteredSuccessfully()
            }
            .addOnFailureListener {e->
                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error while registering the user.",e)
            }
    }

    private fun getCurrentUserID():String
    {
        //Instance of current user using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        // Assigns the currentUserID if it's not null or else keep it blank
        var currentUserID = ""
        if(currentUserID!=null)
        {
            currentUserID = currentUser!!.uid
        }
        return currentUserID
    }

    fun getUserDetails(activity: Activity)
    {
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnCompleteListener{ document ->
                Log.i(activity.javaClass.simpleName, document.toString())

                // here we receive the document snapshot and we convert it into data model object
                val user = document.result.toObject(User::class.java)!!

                /* here MODE_PRIVATE is for default mode means where the created file can only be accessed
                    by the calling application means to all the applicants who shares the same User ID
                */
                val sharedPreferences = activity.getSharedPreferences(
                    Constants.RB_PREFERENCES,Context.MODE_PRIVATE
                )

                // for editing shared preferences
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                /*
                    Key : logged_in_username
                    Value : Tushar Bhut (as example)
                */

                editor.putString(
                    Constants.LOGGED_IN_USERNAME,
                    "${user.firstName} ${user.lastName}"
                )
                editor.apply()
                when(activity)
                {
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)
                    }
                }
            }
    }
}
