package com.example.cw2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cw2.models.Item
import com.example.cw2.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_add.*

/**
 * activity adds item to Firestore 'Items' collection
 */

private const val TAG = "AddActivity"
private const val PICK_PHOTO_CODE = 1234
private const val EXTRA_USERNAME = "EXTRA_USERNAME"

open class AddActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var signedInUser: User? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var firestoreDb: FirebaseFirestore
    private var photoUri: Uri? = null
    private lateinit var storageReference: StorageReference

    //creating spinner items
    private var spinner: Spinner? = null
    private var arrayAdapter:ArrayAdapter<String>? = null
    private var itemList  = arrayOf(
            "Fridge",
            "Freezer",
            "Pantry",
            "Counter"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        //initializing database
        firestoreDb = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        //firestore storage reference
        storageReference = FirebaseStorage.getInstance().reference

        firestoreDb.collection("Users")
                .document(FirebaseAuth.getInstance().currentUser?.uid as String)
                .get()
                .addOnSuccessListener { userSnapshot ->
                    signedInUser = userSnapshot.toObject(User::class.java)

                    Log.i(TAG, "user: ${FirebaseAuth.getInstance().currentUser?.email as String} has signed in")
                }.addOnFailureListener {
                    Log.i(TAG, "user has failed signing in", it)
                }

        //initializing spinner
        spinner = spinners
        arrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, itemList)
        spinner?.adapter = arrayAdapter
        arrayAdapter!!.setDropDownViewResource(R.layout.spinner_layout)
        spinner?.onItemSelectedListener = this

        addButton.setOnClickListener {
            addItem()
        }

        cancelButton.setOnClickListener {
            finish()
        }

        uploadButton.setOnClickListener{
            Log.i(TAG, "Image picker opened up on device")
            val imagePicker =Intent(Intent.ACTION_GET_CONTENT)
            imagePicker.type ="image/*"

            if(imagePicker.resolveActivity(packageManager) != null){
                startActivityForResult(imagePicker, PICK_PHOTO_CODE)
            }
        }
    }

    //function to add an item
    private fun addItem() {
        if (eItemName.text.isBlank()) {
            Toast.makeText(this, "Please add name", Toast.LENGTH_SHORT).show()
            return
        }

        if (photoUri == null){
            Toast.makeText(this, "Pleases select photo", Toast.LENGTH_SHORT).show()
            return
        }

        addButton.isEnabled = true
        cancelButton.isEnabled = true
        uploadButton.isEnabled = true

        val photoUploadUri = photoUri as Uri

        val itemId = firestoreDb.collection("Items").document().id
        val location = spinner?.selectedItem.toString().toLowerCase()

        val photoReference = storageReference.child("images/${System.currentTimeMillis()}-photo.jpg")
        photoReference.putFile(photoUploadUri)
                .continueWithTask {
                    Log.i(TAG, "uploaded bytes: ${it.result?.bytesTransferred}")
                    photoReference.downloadUrl
                }.continueWithTask {
                    //post object with image url to add to "post collection" in firebase
                    val item = Item(
                            itemId,
                            eItemName.text.toString(),
                            eQuantity.text.toString(),
                            it.result.toString(),
                            System.currentTimeMillis(),
                            eExpiringDate.text.toString(),
                            auth.currentUser?.uid.toString(),
                            location
                    )

                    //adding item data to Item table
                    firestoreDb.collection("Items")
                            .add(item)
                            }.addOnCompleteListener {
                                addButton.isEnabled = false
                                cancelButton.isEnabled = false
                                uploadButton.isEnabled = false
                                if (!it.isSuccessful) {
                                    Log.e(TAG, "Error whilst communicating with firebase", it.exception)
                                    Toast.makeText(this, "Failed to add", Toast.LENGTH_SHORT).show()
                                }

                                //clearing fields
                                eItemName.text.clear()
                                eQuantity.text.clear()
                                eExpiringDate.text.clear()

                                chooseImage.setImageResource(0)

                                //success message
                                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

                                //switching to welcome page
                                val intent = Intent(this, WelcomeActivity::class.java)
                                intent.putExtra(EXTRA_USERNAME, signedInUser?.name)
                                startActivity(intent)
                                finish()
                            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_PHOTO_CODE){
            if(resultCode == Activity.RESULT_OK){
                photoUri = data?.data
                Log.i(TAG, "photoUri $photoUri")
                chooseImage.setImageURI(photoUri)
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // no code
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // no code
    }
}