package by.maxnevans.gamelist.view

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat

object Fonts {
    private var fonts: MutableMap<String, Typeface> = mutableMapOf()

    val map: MutableMap<String, Typeface>
        get() {
            return fonts
        }

    val values: ArrayList<String>
        get() {
            return fonts.map { entry -> entry.key} as ArrayList<String>
        }

    fun loadFonts(context: Context) {
        fonts.put("Roboto Light", Typeface.create("sans-serif-light", Typeface.NORMAL))
        fonts.put("Roboto Medium", Typeface.create("sans-serif-medium", Typeface.NORMAL))
        fonts.put("Roboto Condensed", Typeface.create("sans-serif-condensed", Typeface.NORMAL))
        fonts.put("Roboto Thin", Typeface.create("sans-serif-thin", Typeface.NORMAL))
        fonts.put("Roboto Regular", Typeface.create("sans-serif", Typeface.NORMAL))
    }
}