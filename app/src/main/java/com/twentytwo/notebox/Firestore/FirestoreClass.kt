package com.twentytwo.notebox.Firestore

import  com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.twentytwo.notebox.Activities.BdaysActivity.CreateBirthdayActivity
import com.twentytwo.notebox.Activities.CONTACTS.CreateContacts
import com.twentytwo.notebox.Activities.SecurePages.SignupActivity
import com.twentytwo.notebox.Activities.SecurePages.User
import com.twentytwo.notebox.Activities.SecurePages.bdaydata
import com.twentytwo.notebox.Activities.SecurePages.datacontacts
import java.text.SimpleDateFormat
import java.util.*

class FirestoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()

    fun registeraUser(activity: SignupActivity, userInfo: User) {
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

    fun CreatwBirthday(activity:CreateBirthdayActivity,Bdays: bdaydata) {
        val sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")
        val currentday = sdf.calendar.time

        mFireStore.collection("BIRTHDAYS").document(Bdays.created)
            .set(Bdays, SetOptions.merge())
            .addOnSuccessListener {
                activity.CreateBdaySuccess()
            }
            .addOnFailureListener {
                activity.CreateBdayfailure()
            }

    }
    fun UpdateBday(activity:CreateBirthdayActivity,Bdays: bdaydata) {
        val sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")

        mFireStore.collection("BIRTHDAYS").document(Bdays.created)
            .set(Bdays, SetOptions.merge())
            .addOnSuccessListener {
                activity.CreateBdaySuccess()
            }
            .addOnFailureListener {
                activity.CreateBdayfailure()
            }

    }

    fun CreateContacts(activity: CreateContacts, contactData: datacontacts) {
        mFireStore.collection("CONTACTS").document(contactData.created)
            .set(contactData, SetOptions.merge())
            .addOnSuccessListener {
                activity.CC_ContractsSucurd()
            }
            .addOnFailureListener {
                activity.CC_Failed()
            }
    }

}