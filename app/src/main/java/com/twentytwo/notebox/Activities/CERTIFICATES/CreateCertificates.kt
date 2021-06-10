package com.twentytwo.notebox.Activities.CERTIFICATES

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.twentytwo.notebox.Activities.BdaysActivity.BirthdayActivity
import com.twentytwo.notebox.Activities.Notes.Notes
import com.twentytwo.notebox.Activities.SecurePages.certific
import com.twentytwo.notebox.Firestore.FirestoreClass
import com.twentytwo.notebox.R
import kotlinx.android.synthetic.main.activity_create_certificates.*
import kotlinx.android.synthetic.main.cc_contacte_2nd.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CreateCertificates : AppCompatActivity() {


    private var filePath: Uri? = null
    internal var storage: FirebaseStorage? = null
    internal var storageReference: StorageReference? = null
    private val PICK_IMAGE_REQUEST = 1244


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_certificates)
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        chooseBtn.setOnClickListener {
            showFileChoser()
        }
        uploadBtn.setOnClickListener {
            fileUPload()
        }

    }

    private fun fileUPload() {
        val sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")
        val currentday = sdf.calendar.time.toString()

        val uid = FirebaseAuth.getInstance().currentUser.uid

        if (filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading")
            progressDialog.show()

            val imageRef = storageReference!!.child("images/" + UUID.randomUUID().toString())
            imageRef.putFile(filePath!!)
                .addOnSuccessListener { taskSnapshot ->
                    val name = taskSnapshot.metadata!!.name

                    imageRef.getDownloadUrl().addOnSuccessListener(
                        OnSuccessListener<Uri> { uri ->
                            Log.d("TAG", "onSuccess: uri= $uri")
                            ////////////////////////////////////////////
                            val certdata = currentday?.let { it1 ->
                                certific(
                                    uid,
                                    uri.toString(),
                                    desctextCert.text.toString(),
                                    name.toString(),
                                    it1
                                )
                            }
                            if (certdata != null) {
                                FirestoreClass().createCertific(this@CreateCertificates, certdata)
                            }
                            /////////////////////////////////////
                        })


                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "File Uplaioded", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Failed to upload", Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnProgressListener { tasKSnapshot ->
                    val progress =
                        100.0 * tasKSnapshot.bytesTransferred / tasKSnapshot.totalByteCount
                    progressDialog.setMessage("Uploaded   " + progress.toInt() + "   %...")
                }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            val result = CropImage.getActivityResult(data)
            ImagePrewiew.setImageURI(result.uri)
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, result.uri)
            if (data != null) {
                filePath = result.uri
            }
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                ImagePrewiew!!.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun showFileChoser() {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(5,4)
            .setOutputCompressQuality(20)
            .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
            .start(this)

//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(Intent.createChooser(intent, "SELECT PICTURE"), PICK_IMAGE_REQUEST)
    }

    fun certiSuccess() {
        val intent = Intent(this, CertificatesActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)

        Toast.makeText(this, "Bdsay success Success", Toast.LENGTH_SHORT).show()
    }

    fun Certifail() {
        Toast.makeText(this, "bday fsaild Failded to user", Toast.LENGTH_SHORT).show()

    }
}