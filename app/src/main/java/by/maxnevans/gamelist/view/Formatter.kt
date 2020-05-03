package by.maxnevans.gamelist.view

object Formatter {
    fun formatCost(cost: Double): String {
        return "%.2f".format(cost)
    }

    fun formatRating(rating: Double): String {
        return "%.1f".format(rating)
    }

    fun formatCountPlayers(countPlayers: Double): String {
        return "%.0f".format(countPlayers)
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
}