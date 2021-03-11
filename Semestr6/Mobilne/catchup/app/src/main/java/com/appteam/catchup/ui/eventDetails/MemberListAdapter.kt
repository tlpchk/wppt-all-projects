package com.appteam.catchup.ui.eventDetails

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.appteam.catchup.R
import com.appteam.catchup.model.EventMember
import com.appteam.catchup.ui.GlideApp
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.item_user_list.view.*

class MemberListAdapter(
    val context: Context,
    private var eventMembers: ArrayList<EventMember>
) : RecyclerView.Adapter<MemberListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_user_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = eventMembers[position]
        holder.usernameTextView.text = user.username
    }

    override fun getItemCount(): Int {
        return eventMembers.size
    }

    fun deleteMember(member: EventMember){
        eventMembers.remove(member)
        notifyDataSetChanged()
    }

    fun addMember(member: EventMember) {
        eventMembers.add(member)
        eventMembers = ArrayList(eventMembers.distinct())
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var usernameTextView: TextView = itemView.tv_username
    }
}