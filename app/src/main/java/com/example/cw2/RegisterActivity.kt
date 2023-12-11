package com.example.cw2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cw2.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

/**
 * activity registers the user using Firebase authentication
 */

private const val TAG = "RegisterActivity"

class RegisterActivity : AppCompatActivity() {

    private lateinit var firebaseDb: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //initializing firebase cloud store
        firebaseDb = FirebaseFirestore.getInstance()

        //link to register page
        loginLink.setOnClickListener {
            login()
        }

        //submit button
        registerButton.setOnClickListener {
            createUser()
        }
    }

    //function creating user
    private fun createUser() {
        registerButton.isEnabled = true
        val name = registerName.text.toString().trim { it <= ' ' }
        val email = registerEmail.text.toString().trim { it <= ' ' }
        val password = registerPassword.text.toString().trim { it <= ' ' }

        //checking if fields are empty
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
        }else{
            //creating user
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        registerButton.isEnabled = false
                        val firebaseUser: FirebaseUser = it.result!!.user!!

                        //adding user information to 'Users' collection
                        val user = User(firebaseUser.uid, name, email)
                        firebaseDb.collection("Users").add(user)

                        //success message
                        Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()

                        //switching to log in page
                        login()

                    } else {
                        Log.i(TAG, "createUserWithEmail failed", it.exception)

                        //alert message if user is not connected to network
                        Toast.makeText(this, "Check details or network connection", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun login() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}