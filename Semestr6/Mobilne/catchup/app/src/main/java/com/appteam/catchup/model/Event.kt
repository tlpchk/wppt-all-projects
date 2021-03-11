package com.appteam.catchup.model

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude

data class Event(
    val title: String,
    val description: String,
    val photoName: String,
    val dateStart: Timestamp,
    val dateEnd: Timestamp,
    val dateAdded: Timestamp,
    val capacity: Long?,
    val location: LatLng,
    val owner: EventMember,
    val members: ArrayList<EventMember> = arrayListOf(),
    @Exclude val documentId: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Timestamp::class.java.classLoader),
        parcel.readParcelable(Timestamp::class.java.classLoader),
        parcel.readParcelable(Timestamp::class.java.classLoader),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readParcelable(LatLng::class.java.classLoader),
        parcel.readParcelable(EventMember::class.java.classLoader),
        arrayListOf<EventMember>().apply {
            parcel.readList(this, EventMember::class.java.classLoader)
        },
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(photoName)
        parcel.writeParcelable(dateStart, flags)
        parcel.writeParcelable(dateEnd, flags)
        parcel.writeParcelable(dateAdded, flags)
        parcel.writeValue(capacity)
        parcel.writeParcelable(location, flags)
        parcel.writeParcelable(owner, flags)
        parcel.writeList(members)
        parcel.writeString(documentId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}
