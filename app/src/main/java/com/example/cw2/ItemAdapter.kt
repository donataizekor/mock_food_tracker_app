package com.example.cw2

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cw2.models.Item
import kotlinx.android.synthetic.main.activity_update.view.*
import kotlinx.android.synthetic.main.activity_welcome.view.*
import kotlinx.android.synthetic.main.item_row.*
import kotlinx.android.synthetic.main.item_row.view.*

class ItemAdapter(private val context: Context, private val items: List<Item>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //creating variable that will "repeat the layout"
        val view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{

        fun bind(item: Item) {
            itemView.tExpiringDate.text = item.expiring_date
            itemView.tItemName.text = item.name
            itemView.tQuantity.text = item.quantity

            Glide.with(context).load(item.image_url).into(itemView.itemImage)

        }

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface  OnItemClickListener{
        fun onItemClick(position: Int)
    }


}
