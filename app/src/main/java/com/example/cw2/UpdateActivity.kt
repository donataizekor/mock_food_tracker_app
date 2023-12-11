package com.example.cw2

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cw2.models.Item
import com.example.cw2.models.User
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_update.*

/**
 * activity updates item in Firestore 'Items' collection
 */

private const val TAG = "UpdateActivity"

class UpdateActivity : AppCompatActivity() {

    lateinit var firestoreDb: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        //initializing database
        firestoreDb = FirebaseFirestore.getInstance()

        cancelButton.setOnClickListener {
            finish()
        }

        updateButton.setOnClickListener {
//            updateItem()
        }

        deleteButton.setOnClickListener {
//            deleteItem()
        }
    }

//    private fun updateItem() {
//
//        val item = Item()
//        val itemREF = editItem.text.toString().trim { it <= ' ' }
//
//        if (itemREF.isBlank()) {
//            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show()
//
//        }
//            firestoreDb.collection("Items").document()
//                    .update("name", itemREF)
//
//    }
//
//    private fun deleteItem() {
//        TODO("Not yet implemented")
//    }
//
//    private fun retrieveItem(){
//        TODO("Not yet implemented")
//
//    }

}