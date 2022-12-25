package com.android.binapp.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.binapp.R
import com.android.binapp.domain.models.BinInfoEntity
import kotlinx.android.synthetic.main.card_history_item.view.*

class AdapterHistory(
    val context: Context,
    private var items:List<BinInfoEntity>,
    val listener: Listener
):RecyclerView.Adapter<AdapterHistory.ViewHolder>() {

   inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textView = itemView.textViewBinHistory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(context).inflate(R.layout.card_history_item,parent,false)
        val holder = ViewHolder(view)
        view.setOnClickListener{
            val pos = holder.adapterPosition
            if(pos!=RecyclerView.NO_POSITION){
               listener.onItemClick(pos)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position].number.toString()

    }

    override fun getItemCount(): Int {
      return  items.size

    }

    fun getItem(index:Int): BinInfoEntity {
        return  items[index]
    }


   }