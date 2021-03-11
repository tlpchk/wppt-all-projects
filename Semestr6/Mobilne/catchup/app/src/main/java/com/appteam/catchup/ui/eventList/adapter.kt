package com.appteam.catchup.ui.eventList

import android.annotation.SuppressLint
import android.os.Bundle
import com.appteam.catchup.R

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import com.appteam.catchup.MapFragment.Companion.EVENT_ID
import com.appteam.catchup.model.Event
import com.appteam.catchup.model.EventMember
import com.appteam.catchup.ui.GlideApp
import com.appteam.catchup.ui.eventDetails.EventDetailsFragment.Companion.EVENT_DETAILS_BUNDLE

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.event_list_item.view.*
import java.lang.Exception
import java.lang.reflect.Member
import java.text.SimpleDateFormat
import java.util.*


class Adapter (val items: MutableList<Event>, val nav: NavController) : RecyclerView.Adapter<Adapter.VH>(){

    var filter = { item: Event -> true }
    val filterItems by lazy { mutableListOf<Event>().apply { addAll(items.filter(filter) as MutableList<Event>) } }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(parent, nav)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        try {
            holder.bind(filterItems[position])
        } catch (e: Exception) {
            Log.e("dasda", "dsada")
        }
    }

    override fun getItemCount(): Int = items.filter(filter).size

    fun setItemsFilter(newFilter: (Event) -> Boolean ) {
        filter = newFilter
        updateFiltredList()
    }

    fun updateFiltredList() {
        filterItems.clear()
        filterItems.addAll(items.filter(filter) as MutableList<Event>)
        notifyDataSetChanged()
    }

    fun addItem(event: Event) {
        items.add(event)
        updateFiltredList()
        notifyItemInserted(items.size)
    }

    fun removeAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    class VH(parent: ViewGroup,val nav: NavController)  : RecyclerView.ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.event_list_item, parent, false)) {

        private val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        private val visibleMessageLength = 40
        val storage : FirebaseStorage = FirebaseStorage.getInstance()
        val storageReference : StorageReference = storage.reference

        @SuppressLint("SetTextI18n")
        fun bind(event: Event) = with(itemView) {
            itemView.setOnClickListener{
                val bundle = Bundle()
                bundle.putString(EVENT_ID, event.documentId)
                bundle.putParcelable(EVENT_DETAILS_BUNDLE, event)
                nav.navigate(R.id.eventDetailsScreen, bundle)
            }

            storageReference.child("images/" + event.photoName).downloadUrl.addOnSuccessListener {
                GlideApp.with(this)
                    .load(it)
                    .fallback(R.drawable.login_background)
                    .placeholder(R.drawable.login_background)
                    .into(imageView2)
            }.addOnFailureListener {
                imageView2.setImageResource(R.drawable.login_background)
            }

            title.text = event.title

            description.text = if (event.description.length > visibleMessageLength) {
                event.description.take(visibleMessageLength) + "..."
            } else {
                event.description
            }

            startDate.text = format.format(event.dateStart.toDate())

            // if event is unlimited
            if (event.capacity!! > 0) {
                capacity.text = "${with(event) { members.size.toString() }}/$capacity"
            } else {
                capacity.text = with(event) { members.size.toString() }
            }

        }
    }
}