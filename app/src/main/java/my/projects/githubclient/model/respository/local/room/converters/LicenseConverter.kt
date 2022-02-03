package my.projects.githubclient.model.respository.local.room.converters

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import my.projects.githubclient.model.data.License

class LicenseConverter {
    private val moshi: Moshi = Moshi.Builder().build()
    private val jsonAdapter: JsonAdapter<License> = moshi.adapter(License::class.java)

    @TypeConverter
    fun fromJson(json: String): License? = jsonAdapter.fromJson(json)

    @TypeConverter
    fun toJson(license: License?): String = jsonAdapter.toJson(license)
}