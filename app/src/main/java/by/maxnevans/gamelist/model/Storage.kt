package by.maxnevans.gamelist.model

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import by.maxnevans.gamelist.R
import java.net.URL

object Storage {
    private var settingsController = SettingsController()
    private var filtersController = FiltersController()
    private var gamesController = GamesController()

    val settings: SettingsController
        get() {
            return settingsController
        }

    val filters: FiltersController
        get() {
            return filtersController
        }

    val games: GamesController
        get() {
            return gamesController
        }

    fun resolveImage(context: Context, logo: String?): Drawable? {
        val defaultLogo = ResourcesCompat.getDrawable(context.resources,
            R.drawable.default_logo, null)

        return defaultLogo
    }

    fun resolveTrailer(context: Context, trailer: String?): String {
        return trailer ?: ""
    }
}