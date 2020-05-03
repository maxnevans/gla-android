package by.maxnevans.gamelist.model

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import by.maxnevans.gamelist.R

class Storage private constructor() {
    private object HOLDER {
        val INSTANCE = Storage()
    }
    companion object {
        val instance: Storage by lazy { HOLDER.INSTANCE }
    }

    fun resolveImage(context: Context, logo: String?): Drawable? {
        val defaultLogo = ResourcesCompat.getDrawable(context.resources,
            R.drawable.default_logo, null)

        return defaultLogo
    }
}