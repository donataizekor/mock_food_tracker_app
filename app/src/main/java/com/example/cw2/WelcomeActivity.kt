package com.example.cw2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.cw2.activities.CounterActivity
import com.example.cw2.activities.FreezerActivity
import com.example.cw2.activities.FridgeActivity
import com.example.cw2.activities.PantryActivity
import com.example.cw2.models.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_welcome.*

/**
 * activity displays lists card
 */

private const val TAG = "WelcomeActivity"

class WelcomeActivity : AddActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var user: User
    private lateinit var auth : FirebaseAuth
    lateinit var firestoreDb: FirebaseFirestore
    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        //initializing var auth, firebase store and navigation drawer
        auth = FirebaseAuth.getInstance()
        firestoreDb = FirebaseFirestore.getInstance()
        toggle = ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //allows action to be performed on navigation drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this)

        //add item button
        add_button.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        //fridge card
        fridgeCard.setOnClickListener{
            Log.i(TAG, "User clicked on fridge card")
            startActivity(Intent(this, FridgeActivity::class.java))
        }

        //freezer card
        freezerCard.setOnClickListener{
            Log.i(TAG, "User clicked on freezer card")
            startActivity(Intent(this, FreezerActivity::class.java))
        }

        //counter card
        counterCard.setOnClickListener{
            Log.i(TAG, "User clicked on counter card")
            startActivity(Intent(this, CounterActivity::class.java))
        }

        //pantry card
        pantryCard.setOnClickListener{
            Log.i(TAG, "User clicked on pantry card")
            startActivity(Intent(this, PantryActivity::class.java))
        }
    }

    //function to log out the user
    private fun logout() {
        Log.i(TAG, "User has logout")
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //allows user to use back key to close navigation drawer
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    //add actions to items of navigation drawer
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                logout()
                Toast.makeText(this, "You have logged out", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}