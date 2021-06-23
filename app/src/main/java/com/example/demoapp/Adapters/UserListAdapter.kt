package com.example.demoapp.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demoapp.Adapters.UserListAdapter.UserListViewHolder
import com.example.demoapp.R
import com.example.demoapp.UI.UserDetail
import com.example.demoapp.models.UsersData
import de.hdodenhof.circleimageview.CircleImageView

class UserListAdapter(
    var context: Context,
    private val aptList: List<UsersData>
) : RecyclerView.Adapter<UserListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_item_list, parent, false)
        return UserListViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val model = aptList[position]
        holder.tvname.text = String.format(
            "%s %s %s",
            model.title,
            model.firstName,
            model.lastName
        )
        holder.tvemail.text = model.email
        Glide.with(context)
            .load(model.picture)
            .into(holder.userimg)
        holder.itemView.setOnClickListener {
            val i = Intent(context, UserDetail::class.java)
            i.putExtra("id", model.id)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return aptList.size
    }

    class UserListViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvname: TextView
        var tvemail: TextView
        var userimg: CircleImageView

        init {
            tvname = itemView.findViewById(R.id.user_name)
            tvemail = itemView.findViewById(R.id.email)
            userimg = itemView.findViewById(R.id.user_img)
        }
    }

}