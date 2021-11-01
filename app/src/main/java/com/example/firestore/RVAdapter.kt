package com.example.firestore

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class RVAdapter (val activity:MainActivity) : RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    private var list= emptyList<Note>()
    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = list[position]


        holder.itemView.apply {

            if(position%2==0){
                linear.setBackgroundColor(Color.parseColor("#e0e0e0"))
                delbtn.setBackgroundColor(Color.parseColor("#e0e0e0"))
                editbtn.setBackgroundColor(Color.parseColor("#e0e0e0"))
            }
            else{
                linear.setBackgroundColor(Color.parseColor("WHITE"))
                delbtn.setBackgroundColor(Color.parseColor("WHITE"))
                editbtn.setBackgroundColor(Color.parseColor("WHITE"))
            }
            textView.text = " ${position+1} - ${data.note}"
            delbtn.setOnClickListener(){
                activity. confirmAlert(data.id)

            }
            editbtn.setOnClickListener {
                activity.customAlert(data.id)
            }

        }
    }

    override fun getItemCount(): Int = list.size
    fun update(list:List<Note>){
        this.list=list
        notifyDataSetChanged()
    }
}