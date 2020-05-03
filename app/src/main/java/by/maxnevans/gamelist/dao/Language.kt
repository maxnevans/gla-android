package by.maxnevans.gamelist.dao

import kotlinx.serialization.*

@Serializable
enum class Language(val internationalName: String, val nativeName: String) {
    ENGLISH("English", "English"),
    RUSSIAN("Russian", "Русский")
}