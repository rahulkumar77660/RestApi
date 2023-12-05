package com.example.restapirecyclerview

import android.content.Context
import android.icu.text.CaseMap.Title
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

interface getItemId{

    fun getItemPosition(position: Int,id : Int,title: String,userId : Int)
    fun deleteItem(position: Int,id: Int)
}
class MyAdapter(val context : Context,val userList: List<PostDataItem>,val getItemId: getItemId) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val userId : TextView = itemView.findViewById(R.id.userId)
        val userTitle : TextView = itemView.findViewById(R.id.title)
        val moreHorizontal : ImageView = itemView.findViewById(R.id.moreHorizontal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.row_desing_to_data,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userPosition = userList[position]
        holder.userId.text=userPosition.userId.toString()
        holder.userTitle.text = userPosition.title

        holder.moreHorizontal.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.moreHorizontal)
            popupMenu.inflate(R.menu.menu_menu_update_delet)

            popupMenu.setOnMenuItemClickListener {

                when(it.itemId){
                    R.id.update->{
                        getItemId.getItemPosition(position,userPosition.id.toString().toInt(),userPosition.title,userPosition.userId)
                    }
                    R.id.delete->{
                        getItemId.deleteItem(position,userPosition.id!!.toInt())
                    }
                }
                true
            }
            popupMenu.show()
        }
    }
}