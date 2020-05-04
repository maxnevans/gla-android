package by.maxnevans.gamelist.model

import by.maxnevans.gamelist.model.dao.Language
import by.maxnevans.gamelist.model.dao.Settings

object SettingsBuilder {
    val DEFAULT_LANGUAGE = Language.ENGLISH
    val DEFAULT_FONT_SIZE = 14
    val DEFAULT_FONT_FAMILY = "Roboto Regular"
    val MAX_FONT_SIZE = 26
    val MIN_FONT_SIZE = 12
    val DIFF_FONT_SIZE = MAX_FONT_SIZE - MIN_FONT_SIZE

    fun buildDefault(): Settings {
        return Settings(DEFAULT_LANGUAGE, DEFAULT_FONT_SIZE, DEFAULT_FONT_FAMILY)
    }
}