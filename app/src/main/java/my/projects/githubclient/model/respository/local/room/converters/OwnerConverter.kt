package my.projects.githubclient.model.respository.local.room.converters

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import my.projects.githubclient.model.data.Owner

class OwnerConverter {
    private val moshi: Moshi = Moshi.Builder().build()
    private val jsonAdapter: JsonAdapter<Owner> = moshi.adapter(Owner::class.java)

    @TypeConverter
    fun fromJson(json: String): Owner = jsonAdapter.fromJson(json)!!

    @TypeConverter
    fun toJson(owner: Owner?): String = jsonAdapter.toJson(owner)
}