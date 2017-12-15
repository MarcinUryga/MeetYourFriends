package com.example.marcin.meetfriends.ui.event_detail.viewmodel

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by marci on 2017-12-03.
 */
data class EventBasicInfo(
    val id: String? = null,
    val iconId: String? = null,
    val organizerId: String? = null,
    val name: String? = null,
    var description: String? = null
) : Parcelable {

  constructor(parcel: Parcel) : this(
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readString()) {
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(id)
    parcel.writeString(iconId)
    parcel.writeString(organizerId)
    parcel.writeString(name)
    parcel.writeString(description)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<EventBasicInfo> {
    override fun createFromParcel(parcel: Parcel): EventBasicInfo {
      return EventBasicInfo(parcel)
    }

    override fun newArray(size: Int): Array<EventBasicInfo?> {
      return arrayOfNulls(size)
    }
  }
}