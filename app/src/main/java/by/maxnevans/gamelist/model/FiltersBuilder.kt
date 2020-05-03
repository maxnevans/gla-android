package by.maxnevans.gamelist.model

import by.maxnevans.gamelist.dao.Filters

object FiltersBuilder {
    val MIN_COST = 0.0
    val MAX_COST = 100000.0
    var DIFF_COST = MAX_COST - MIN_COST
    val MIN_RATING = 0.0
    val MAX_RATING = 5.0
    val DIFF_RATING = MAX_RATING - MIN_RATING
    val MIN_COUNT_PLAYERS = 0.0
    val MAX_COUNT_PLAYERS = 1e12
    val DIFF_COUNT_PLAYERS = MAX_COUNT_PLAYERS - MIN_COUNT_PLAYERS

    fun buildDefault(): Filters {
        return Filters(MIN_COST, MAX_COST, MIN_RATING, MAX_RATING, MIN_COUNT_PLAYERS, MAX_COUNT_PLAYERS)
    }
}