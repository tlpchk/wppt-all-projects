package com.am.gallery.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Photo(val url: String? = null, val filePath: String? = null, var description: String? = null, var rating: Float = 0f) : Parcelable{
    private val id : Int

    init {
        id = nextId
        nextId++
    }

    companion object{
        var nextId = 1
    }


    fun getId(): Int{
        return id
    }
}