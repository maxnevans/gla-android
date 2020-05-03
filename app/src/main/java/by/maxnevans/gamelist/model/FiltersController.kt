package by.maxnevans.gamelist.model

import by.maxnevans.gamelist.dao.Filters
import by.maxnevans.gamelist.dao.Game
import by.maxnevans.gamelist.dao.Settings
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.util.logging.Filter

class FiltersController internal constructor(): UpdatableController<Filters>(){
    override var obj: Filters = FiltersBuilder.buildDefault()
    private var filters: Filters
        get() {
            return obj
        }
        set(value) {
            obj = value
        }

    var maxCost: Double
        get() {
            return filters.maxCost
        }
        set(cost) {
            filters.maxCost = cost
            notifyChange()
        }

    var minCost: Double
        get() {
            return filters.minCost
        }
        set(cost) {
            filters.minCost = cost
            notifyChange()
        }

    var maxRating: Double
        get() {
            return filters.maxRating
        }
        set(rating) {
            filters.maxRating = rating
            notifyChange()
        }

    var minRating: Double
        get() {
            return filters.minRating
        }
        set(rating) {
            filters.minRating = rating
            notifyChange()
        }

    var maxCountPlayers: Double
        get() {
            return filters.maxCountPlayers
        }
        set(countPlayers) {
            filters.maxCountPlayers = countPlayers
            notifyChange()
        }

    var minCountPlayers: Double
        get() {
            return filters.minCountPlayers
        }
        set(countPlayers) {
            filters.minCountPlayers = countPlayers
            notifyChange()
        }

    fun reset() {
        filters = FiltersBuilder.buildDefault()
        notifyChange(true)
    }

    fun toJson(): String {
        val json = Json(JsonConfiguration.Stable)
        return json.stringify(Filters.serializer(), filters)
    }

    fun fromJson(json: String) {
        val js = Json(JsonConfiguration.Stable)
        filters = js.parse(Filters.serializer(), json)
    }

    fun checkRating(rating: Double): Boolean {
        return rating >= filters.minRating && rating <= filters.maxRating
    }

    fun checkCountPlayers(countPlayers: Double): Boolean {
        return countPlayers >= filters.minCountPlayers && countPlayers <= filters.maxCountPlayers
    }

    fun checkCost(cost: Double): Boolean {
        return cost >= filters.minCost && cost <= filters.maxCost
    }

    fun checkGame(game: Game): Boolean {
        return checkRating(game.rating) && checkCost(game.cost) && checkCountPlayers(game.countPlayers)
    }
}