package com.appteam.catchup.ui.eventDetails


import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.appteam.catchup.MapFragment
import com.appteam.catchup.R
import com.appteam.catchup.firebase.service.EventService
import com.appteam.catchup.model.Event
import com.appteam.catchup.model.EventMember
import com.appteam.catchup.notification.NotificationService
import com.appteam.catchup.ui.GlideApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_event_details.view.*
import org.ocpsoft.prettytime.PrettyTime
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class EventDetailsFragment : Fragment() {

    private lateinit var storageRef: StorageReference
    private lateinit var navController: NavController
    private lateinit var event: Event
    private val auth = FirebaseAuth.getInstance()
    private val meAsEventMember = EventMember(auth.currentUser!!.displayName!!)
    private lateinit var adapter : MemberListAdapter

    companion object {
        const val EVENT_DETAILS_BUNDLE = "EVENT_DETAILS_BUNDLE"
    }

    private fun getEventFromBundle(): Event? {
        return arguments?.getParcelable(EVENT_DETAILS_BUNDLE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_event_details, container, false)

        storageRef = FirebaseStorage.getInstance().reference
        navController = findNavController()

        event = getEventFromBundle()!!

        loadThematicPicture(view)
        setTitle(view)
        setPresentDate(view)
        setDateAdded(view)
        setDescription(view)
        setDates(view)
        setOwner(view)
        setAddress(view)
        setMemberList(view)

        when {
            event.owner == meAsEventMember -> {
                makeButtonDelete(view.btn_action)
            }
            event.members.contains(meAsEventMember) -> {
                makeButtonRefuse(view.btn_action)
            }
            else -> {
                makeButtonJoin(view.btn_action)
            }
        }

        view.tv_see_on_map.setOnClickListener { seeOnMap() }

        return view
    }

    private fun loadThematicPicture(
        view: View,
        pictureReference: StorageReference = storageRef.child("images/${event.photoName}")
    ) {
        val imageView = view.iv_thematic_picture

        pictureReference
            .downloadUrl
            .addOnSuccessListener {
                GlideApp.with(this)
                    .load(pictureReference)
                    .fallback(R.drawable.login_background)
                    .placeholder(R.drawable.login_background)
                    .centerCrop()
                    .into(imageView)
            }
    }

    private fun setTitle(eventDetailsView: View) {
        eventDetailsView.tv_event_title.text = event.title
    }

    private fun setOwner(eventDetailsView: View) {
        val spannable = SpannableString("Organizer: ${event.owner.username}")
        spannable.setSpan(
            ForegroundColorSpan(Color.LTGRAY),
            0,
            "Organizer: ".length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        eventDetailsView.tv_owner.setText(spannable, TextView.BufferType.SPANNABLE)
    }

    private fun setPresentDate(eventDetailsView: View) {
        val date = event.dateStart.toDate()
        val month = SimpleDateFormat("MMM", Locale.US).format(date)
        eventDetailsView.tv_present_date_month.text = month

        val day = SimpleDateFormat("dd", Locale.US).format(date)
        eventDetailsView.tv_present_date_day.text = day
    }

    private fun setDateAdded(eventDetailsView: View) {
        val pretty = PrettyTime(Locale.US)
        val result = pretty.format(event.dateAdded.toDate())

        val spannable = SpannableString("Created: $result")
        spannable.setSpan(ForegroundColorSpan(Color.LTGRAY), 0, "Created: ".length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        eventDetailsView.tv_date_added.text = spannable
    }

    private fun setDescription(eventDetailsView: View) {
        eventDetailsView.tv_event_description.text = event.description
    }

    private fun setDates(eventDetailsView: View) {
        val formatter = SimpleDateFormat("d MMM HH:mm", Locale.US)
        val dateStart = formatter.format(event.dateStart.toDate())
        val dateEnd = formatter.format(event.dateEnd.toDate())
        eventDetailsView.tv_dates.text = "$dateStart – $dateEnd"
    }

    private fun setAddress(eventDetailsView: View) {
        eventDetailsView.tv_address.text = ""
        val thread = FindPlaces()
        thread.textView = eventDetailsView.tv_address
        thread.execute(event.location.latitude.toString(),
                event.location.longitude.toString())
    }


    private fun setCapacity(eventDetailsView: View){
        if((event.capacity!!) != 0L) {
            eventDetailsView.tv_members.text =  "Members: ${event.members.distinct().size} / ${event.capacity}"
        }else{
            eventDetailsView.tv_members.text = "Members"
        }
    }


    private fun setMemberList(eventDetailsView: View) {
        setCapacity(eventDetailsView)
        adapter = MemberListAdapter(activity!!, event.members)
        eventDetailsView.rv_members.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        eventDetailsView.rv_members.adapter =adapter
    }


    private fun seeOnMap() {
        val bundle = Bundle()
        bundle.putParcelable(MapFragment.EVENT_POSITION, event.location)
        navController.navigate(R.id.action_eventDetails_to_mapsActivity, bundle)
    }


    private fun makeButtonRefuse(button: Button) {
        setButtonContent(button, R.string.refuse, android.R.drawable.ic_delete)

        button.setOnClickListener {
            EventService.deleteCurrentUserFromEvent(event.documentId!!, meAsEventMember)
                .addOnSuccessListener {
                    makeButtonJoin(button)
                    adapter.deleteMember(meAsEventMember)
                    setCapacity(view!!)
                    Toast.makeText(context, "You was deleted from event", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun makeButtonJoin(button: Button) {
        setButtonContent(button, R.string.join, android.R.drawable.ic_input_add)

        button.setOnClickListener {
            EventService.addCurrentUserToEvent(event.documentId!!, meAsEventMember)
                .addOnSuccessListener {
                    makeButtonRefuse(button)
                    adapter.addMember(meAsEventMember)
                    setCapacity(view!!)
                    Toast.makeText(context, "You was added to event", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            NotificationService.scheduleNotification(context!!, 314, event)
        }
    }

    private fun makeButtonDelete(button: Button) {
        setButtonContent(button, R.string.delete, android.R.drawable.ic_menu_delete)

        button.setOnClickListener {
            EventService.deleteEvent(event.documentId!!)
                .addOnSuccessListener {
                    navController.popBackStack()
                    Toast.makeText(context, "Event was deleted", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun setButtonContent(button: Button, textResourceId: Int, iconDrawableId: Int) {
        button.text = resources.getString(textResourceId)
        val icon = context!!.resources.getDrawable(iconDrawableId)
        icon.setBounds(0, 0, icon.intrinsicHeight, icon.intrinsicWidth)
        button.setCompoundDrawables(icon, null, null, null)
    }

    /**
     * Reverse geocoding in asyncTask
     */

    inner class FindPlaces : AsyncTask<String, Void, List<Address>?>() {
        lateinit var textView : TextView

        override fun doInBackground(vararg params: String?): List<Address>? {
            if (activity == null) {
                this.cancel(true)
            }
            val geocoder = Geocoder(activity, Locale.getDefault())
            var addresses: List<Address>? = null
            try {
                addresses = geocoder.getFromLocation(
                    params[0]!!.toDouble(),
                    params[1]!!.toDouble()
                    , 1
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return addresses
        }

        override fun onPostExecute(addresses: List<Address>?) {
            super.onPostExecute(addresses)
            if (activity == null) {
                this.cancel(true)
            }

            if (addresses == null || addresses.isEmpty()) {
                return
            }

            val address = addresses[0]
            textView.text = address.getAddressLine(0)
        }
    }
}
