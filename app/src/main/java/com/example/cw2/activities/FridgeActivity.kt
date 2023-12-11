package com.example.cw2.activities

import android.os.Bundle
import android.util.Log
import com.example.cw2.ItemActivity
import com.example.cw2.models.Item
import com.google.firebase.auth.FirebaseAuth

/**
 * activity displays items from Firestore which are labelled as fridge
 */

private const val TAG = "FridgeActivity"

class FridgeActivity : ItemActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initializing auth variable
        auth = FirebaseAuth.getInstance()

        //query for all items of the signed in user
        val itemReference = firestoreDb
            .collection("Items")
                .whereEqualTo("user", auth.currentUser?.uid.toString())

        //query for items labelled as fridge
        val typeReference = itemReference
                .whereEqualTo("type", "fridge")

        // keeping the database up to date
        typeReference.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.e(TAG, "Exception when querying db", exception)
                return@addSnapshotListener
            }
            val itemList = snapshot.toObjects(Item::class.java)

            //clear from previous data
            items.clear()

            //updating when receiving a new post/data
            items.addAll(itemList)

            //update adapter
            adapter.notifyDataSetChanged()

            for (item in itemList) {
                Log.i(TAG, "Item $item")
            }
        }
    }
}