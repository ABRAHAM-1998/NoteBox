package com.twentytwo.notebox.Firestore
import android.widget.Toast
import  com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.twentytwo.notebox.Activities.SignupActivity
import com.twentytwo.notebox.Models.User

class FirestoreClass {
    private val mFireStore =FirebaseFirestore.getInstance()

    fun registeraUser( activity: SignupActivity,userInfo:User){
        mFireStore.collection("UsersDetails")
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener {
                activity.userRegistrationFailure()
            }

    }

}