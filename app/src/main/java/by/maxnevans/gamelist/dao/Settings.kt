package by.maxnevans.gamelist.dao
import kotlinx.serialization.*

@Serializable
data class Settings(var language: Language, var fontSize: Int, var fontFamily: String)


