package com.appteam.catchup.model

import android.os.Parcel
import android.os.Parcelable


data class EventMember(val username : String?) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EventMember> {
        override fun createFromParcel(parcel: Parcel): EventMember {
            return EventMember(parcel)
        }

        override fun newArray(size: Int): Array<EventMember?> {
            return arrayOfNulls(size)
        }
    }
}