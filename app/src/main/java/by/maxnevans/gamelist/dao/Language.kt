package by.maxnevans.gamelist.dao

import kotlinx.serialization.*

@Serializable
enum class Language(val internationalName: String, val nativeName: String, val code: String) {
    ENGLISH("English", "English", "en"),
    RUSSIAN("Russian", "Русский", "ru")
}