package com.twentytwo.notebox.Firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.twentytwo.notebox.Activities.BdaysActivity.CreateBirthdayActivity
import com.twentytwo.notebox.Activities.CERTIFICATES.CreateCertificates
import com.twentytwo.notebox.Activities.CONTACTS.CreateContacts
import com.twentytwo.notebox.Activities.LOCKER.CreateLockeritems
import com.twentytwo.notebox.Activities.Notes.Create_Notes
import com.twentytwo.notebox.Activities.SECRETDOOR.Developer_Update
import com.twentytwo.notebox.Activities.SecurePages.*
import java.text.SimpleDateFormat

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

    fun CreatwBirthday(activity: CreateBirthdayActivity, Bdays: bdaydata) {
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

    fun UpdateBday(activity: CreateBirthdayActivity, Bdays: bdaydata) {
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

    fun CreateLOCkerData(activty: CreateLockeritems, dataLocker: data_locker) {
        mFireStore.collection("LOCKER").document(dataLocker.created)
            .set(dataLocker, SetOptions.merge())
            .addOnSuccessListener {
                activty.Create_LockerSuccess()
            }
            .addOnFailureListener {
                activty.Create_LockerFailed()
            }
    }

    fun CreateNotes(activity: Create_Notes, noteData: notes) {
        mFireStore.collection("NOTES").document(noteData.created)
            .set(noteData, SetOptions.merge())
            .addOnSuccessListener {
                activity.CreateNotesSuccess()
            }
            .addOnFailureListener {
                activity.CreateNotesfails()
            }
    }

    fun createCertific(activity: CreateCertificates, certdata: certific) {
        mFireStore.collection("CERTIFICATES").document(certdata.created)
            .set(certdata, SetOptions.merge())
            .addOnSuccessListener {
                activity.certiSuccess()
            }
            .addOnFailureListener {
                activity.Certifail()
            }

    }

    fun CreateDEV(activity: Developer_Update, devUpate: devUpate) {
        mFireStore.collection("DEVELOPER").document("SECRET")
            .set(devUpate, SetOptions.merge())
            .addOnSuccessListener {
                activity.DevSUCCESS()
            }
            .addOnFailureListener {
                activity.DevFAILED()
            }
    }
}

