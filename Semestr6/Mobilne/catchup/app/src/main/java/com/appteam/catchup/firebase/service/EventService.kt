package com.appteam.catchup.firebase.service

import android.util.Log
import com.appteam.catchup.model.Event
import com.appteam.catchup.TAG
import com.appteam.catchup.model.EventMember
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 *
 * Class for providing events from firebase cloud firestore to the map and vice versa
 */
object EventService {

    var events = HashMap<String, Event>()

    fun eventProvider(): Task<QuerySnapshot> {
        return FirebaseFirestore.getInstance().collection("events").get()
            .addOnSuccessListener { documents ->
                for (document in documents.documents) {
                    Log.d(TAG, "${document.id} => ${document.get("location")}")
                    val event = createEventFromDocuments(document)
                    events[document.id] = event
                }

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun createEventFromDocuments(document: DocumentSnapshot): Event {
        // Fetch latLng
        val latLng = LatLng(
            document.getDouble("location.latitude")!!,
            document.getDouble("location.longitude")!!
        )

        // Fetch capacity from document
        val capacity = if (document.get("capacity") == null) {
            0
        } else {
            document.getLong("capacity")
        }

        // Fetch owner from document
        val hashMapOwner = document.get("owner") as HashMap<String, String>
        Log.d(TAG, hashMapOwner.toString())
        val owner = EventMember(hashMapOwner["username"]!!)

        // Fetch member from document
        val hashMapsMember = document.get("members") as ArrayList<HashMap<String, String>>
        val members: ArrayList<EventMember> = arrayListOf()
        for (hashMap in hashMapsMember) {
            members.add(EventMember(hashMap["username"]!!))
        }

        return Event(
            document.get("title") as String,
            document.get("description") as String,
            document.get("photoName") as String,
            document.get("dateStart") as Timestamp,
            document.get("dateEnd") as Timestamp,
            document.get("dateAdded") as Timestamp,
            capacity,
            latLng,
            owner = owner,
            members = members,
            documentId = document.id
        )
    }

    /**
     * Adding event to database.
     * For perform simple and compound queries in Cloud Firestore
     * subcollection called "location" are added for further collection group queries.
     * That allows to read only event's location instead of all event.
     * See : https://firebase.google.com/docs/firestore/query-data/queries#collection-group-query
     */

    fun addEvent(event: Event): Task<DocumentReference> {
        return FirebaseFirestore.getInstance().collection("events").add(event)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                events[documentReference.id] = event
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }

    fun deleteCurrentUserFromEvent(eventId: String, member: EventMember): Task<Void> {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val eventRef = db.collection("events").document(eventId)
        events.get(eventId)!!.members.remove(member)
        return eventRef.update("members", FieldValue.arrayRemove(member))
    }

    fun addCurrentUserToEvent(eventId: String, member: EventMember): Task<Void> {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val eventRef = db.collection("events").document(eventId)
        events.get(eventId)!!.members.add(member)
        return eventRef.update("members", FieldValue.arrayUnion(member))
    }

    fun deleteEvent(eventId: String): Task<Void> {
        return FirebaseFirestore.getInstance().collection("events")
            .document(eventId)
            .delete().addOnSuccessListener {
                events.remove(eventId)
            }
    }
}

