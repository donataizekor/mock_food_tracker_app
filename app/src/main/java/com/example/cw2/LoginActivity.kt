package com.example.cw2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

/**
 * activity logs in the user using Firebase authentication
 */

private const val TAG = "LoginActivity"

open class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //checking if user is logged in
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            homepage()
        }

        //link to register page
        registerLink.setOnClickListener {
            register()
        }

        loginButton.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        loginButton.isEnabled = true
        val email = loginEmail.text.toString().trim { it <= ' ' }
        val password = loginPassword.text.toString().trim { it <= ' ' }

        if (email.isBlank() || password.isBlank()) {
            //checking if fields are empty
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
        } else {
            //user authentication
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        loginButton.isEnabled = false

                        //success message
                        Toast.makeText(this, "You have logged in", Toast.LENGTH_SHORT).show()

                        //switching to homepage
                        homepage()
                    } else {
                        Log.i(TAG, "signInWithEmail failed", it.exception)

                        //alert message for incorrect details
                        Toast.makeText(this, "Incorrect details or check network", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun register() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun homepage() {
         Log.i(TAG, "goWelcomeActivity")
         val intent = Intent(this, WelcomeActivity::class.java)
         intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
         startActivity(intent)
         finish()
    }
}