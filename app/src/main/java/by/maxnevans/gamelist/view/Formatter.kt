package by.maxnevans.gamelist.view

import android.content.Context
import by.maxnevans.gamelist.R

object Formatter {
    private var context: Context? = null

    fun setupResourceSource(context: Context) {
        this.context = context
    }

    fun formatCost(cost: Double): String {
        return "%.2f".format(cost)
    }

    fun formatRating(rating: Double): String {
        return "%.1f".format(rating)
    }

    fun formatCountPlayers(countPlayers: Double): String {
        return convertDecimalToWord(countPlayers, 0)
    }

    fun formatCostRange(lowerCost: Double, higherCost: Double): String {
        return dashFormat(formatCost(lowerCost), formatCost(higherCost))
    }

    fun formatRatingRange(lowerRating: Double, higherRating: Double): String {
        return dashFormat(formatRating(lowerRating), formatRating(higherRating))
    }

    fun formatCountPlayersRange(lowerPlayers: Double, higherPlayers: Double): String {
        return dashFormat(formatCountPlayers(lowerPlayers), formatCountPlayers(higherPlayers))
    }

    fun formatName(name: String): String {
        return name
    }

    private fun dashFormat(a: String, b: String): String {
        return "$a - $b"
    }

    private fun convertDecimalToWord(value: Double, maxPadDigits: Int): String {
        val f = "%.${maxPadDigits}f"
        return when {
            (value >= 10e9) -> "%.0f".format(value / 1e9) + rStr(R.string.billions) + "+"
            (value >= 5e9) -> "%.1f".format(value / 1e9) + rStr(R.string.billions) + "+"
            (value >= 2e9) -> "%.2f".format(value/ 1e9) + rStr(R.string.billions) + "+"
            (value >= 1e9) -> "%.2f".format(value/ 1e9) + rStr(R.string.billion) + "+"
            (value >= 10e6) -> "%.0f".format(value / 1e6) + rStr(R.string.millions) + "+"
            (value >= 5e6) -> "%.1f".format(value / 1e6) + rStr(R.string.millions) + "+"
            (value >= 2e6) -> "%.2f".format(value/ 1e6) + rStr(R.string.millions) + "+"
            (value >= 1e6) -> "%.2f".format(value/ 1e6) + rStr(R.string.million) + "+"
            (value >= 10e3) -> "%.0f".format(value / 1e3) + rStr(R.string.thousands) + "+"
            (value >= 5e3) -> "%.1f".format(value / 1e3) + rStr(R.string.thousands) + "+"
            (value >= 2e3) -> "%.2f".format(value/ 1e3) + rStr(R.string.thousands) + "+"
            (value >= 1e3) -> "%.2f".format(value/ 1e3) + rStr(R.string.thousand) + "+"
            else -> f.format(value)
        }
    }

    private fun rStr(id: Int): String {
        return context?.getString(id) ?: ""
    }
}