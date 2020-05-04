package by.maxnevans.gamelist.model

import by.maxnevans.gamelist.model.dao.Language
import by.maxnevans.gamelist.model.dao.Settings
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

class SettingsController internal constructor(): UpdatableController<Settings>() {
    override var obj: Settings = SettingsBuilder.buildDefault()
    private var settings: Settings
        get() {
            return obj
        }
        set(value) {
            obj = value
        }

    var fontSize: Int
        get() {
            return settings.fontSize
        }
        set(size) {
            settings.fontSize = size
            notifyChange()
        }

    var fontFamily: String
        get() {
            return settings.fontFamily
        }
        set(family) {
            settings.fontFamily = family
            notifyChange()
        }

    var language: Language
        get() {
            return settings.language
        }
        set(language) {
            settings.language = language
            notifyChange()
        }

    fun reset() {
        settings = SettingsBuilder.buildDefault()
        notifyChange()
    }

    fun toJson(): String {
        val json = Json(JsonConfiguration.Stable)
        return json.stringify(Settings.serializer(), settings)
    }

    fun fromJson(json: String) {
        val js = Json(JsonConfiguration.Stable)
        settings = js.parse(Settings.serializer(), json)
    }
}