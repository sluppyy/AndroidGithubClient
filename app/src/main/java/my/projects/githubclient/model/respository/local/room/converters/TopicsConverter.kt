package my.projects.githubclient.model.respository.local.room.converters

import androidx.room.TypeConverter

class TopicsConverter {
    @TypeConverter
    fun toDB(topics: List<String>) = topics.joinToString("$$$")

    @TypeConverter
    fun fromDB(str: String): List<String> = str.split("$$$")
}