package by.maxnevans.gamelist.model

import by.maxnevans.gamelist.dao.Language
import by.maxnevans.gamelist.dao.Settings

object SettingsBuilder {
    val DEFAULT_LANGUAGE = Language.ENGLISH
    val DEFAULT_FONT_SIZE = 14
    val DEFAULT_FONT_FAMILY = "Tahoma"

    fun buildDefault(): Settings {
        return Settings(DEFAULT_LANGUAGE, DEFAULT_FONT_SIZE, DEFAULT_FONT_FAMILY)
    }
}