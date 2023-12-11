package com.example.cw2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cw2.models.Item
import com.example.cw2.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_item.*
import kotlinx.android.synthetic.main.activity_item.add_button
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.android.synthetic.main.item_row.*

/**
 * activity fetch items from Firestore 'Items' collection
 */

private const val TAG = "ItemActivity"

open class ItemActivity : AppCompatActivity(), ItemAdapter.OnItemClickListener {

    private lateinit var auth: FirebaseAuth
    private var signedInUser: User? = null

    //defining database
    lateinit var firestoreDb: FirebaseFirestore

    //defining item
    lateinit var items: MutableList<Item>

    //defining post adapter
    lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        firestoreDb = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        add_button.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
            finish()
        }

        //layout file for one post
        //data source
        items = mutableListOf()

        //adapter
        adapter = ItemAdapter(this, items, this)

        //bind adapter to layout manager
        recycleItem.adapter = adapter
        recycleItem.layoutManager = LinearLayoutManager(this)

        //firestoreDB is the root of our database
        firestoreDb = FirebaseFirestore.getInstance()

        //query database
        firestoreDb.collection("Users")
            //can view post only when logged in
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)

                Log.i(
                    TAG,
                    "user: ${FirebaseAuth.getInstance().currentUser?.email as String} has signed in"
                )
            }.addOnFailureListener {
                Log.i(TAG, "user has failed signing in", it)
            }

        val itemReference = firestoreDb
            //referencing to the Items "collection in firebase"
            .collection("Items")
            .orderBy("creation_time", Query.Direction.DESCENDING)

        itemReference.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.e(TAG, "Exception when querying post", exception)
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

    override fun onItemClick(position: Int) {
        val clickedItem = items[position]
        val intent = Intent(this, UpdateActivity::class.java)
        startActivity(intent)
    }
}



