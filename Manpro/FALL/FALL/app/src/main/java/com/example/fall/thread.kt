package com.example.fall

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class thread(
    var id_genre: String?,
    var id_user: String?,
    var hirarki: String?,
    var judul: String?,
    var isi: String?,
    var like: Long?,
    var dislike: Long?,
    var date: Date
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readSerializable() as Date
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id_genre)
        parcel.writeString(id_user)
        parcel.writeString(hirarki)
        parcel.writeString(judul)
        parcel.writeString(isi)
        parcel.writeLong(like!!)
        parcel.writeLong(dislike!!)
        parcel.writeSerializable(date)
    }

    override fun describeContents(): Int {
        return 0
    }


    // Setter untuk field content
    fun setIdgenre(content: String?) {
        this.id_genre = content
    }

    // Getter untuk field content
    fun getIdgenre(): String? {
        return this.id_genre
    }

    // Setter untuk field votes
    fun setIduser(id_user: String?) {
        this.id_user = id_user
    }

    // Getter untuk field id_user
    fun getIduser(): String? {
        return this.id_user
    }

    // Setter untuk field votes
    fun sethirarki(hirarki: String?) {
        this.hirarki = hirarki
    }

    // Getter untuk field hirarki
    fun gethirarki(): String? {
        return this.hirarki
    }

    // Setter untuk field votes
    fun setisi(isi: String?) {
        this.isi = isi
    }

    // Getter untuk field isi
    fun getisi(): String? {
        return this.isi
    }

    // Setter untuk field timestamp
    fun setlike(like: Long?) {
        this.like = like
    }

    // Getter untuk field timestamp
    fun getlike(): Long? {
        return this.like
    }

    // Setter untuk field timestamp
    fun setdislike(dislike: Long?) {
        this.dislike = dislike
    }

    // Getter untuk field timestamp
    fun getdislike(): Long? {
        return this.dislike
    }

    companion object CREATOR : Parcelable.Creator<thread> {
        override fun createFromParcel(parcel: Parcel): thread {
            return thread(parcel)
        }

        override fun newArray(size: Int): Array<thread?> {
            return arrayOfNulls(size)
        }
    }
}
