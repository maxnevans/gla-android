package by.maxnevans.gamelist.model.dao

import kotlinx.serialization.Serializable

@Serializable
data class Filters(var minCost: Double,
                   var maxCost: Double,
                   var minRating: Double,
                   var maxRating: Double,
                   var minCountPlayers: Double,
                   var maxCountPlayers: Double)