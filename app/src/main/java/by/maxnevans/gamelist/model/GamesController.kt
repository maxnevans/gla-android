package by.maxnevans.gamelist.model

import by.maxnevans.gamelist.dao.Filters
import by.maxnevans.gamelist.dao.Game
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

class GamesController internal constructor(): UpdatableController<ArrayList<Game>>() {
    override var obj: ArrayList<Game> = arrayListOf()
    private var games: ArrayList<Game>
        get() {
            return obj
        }
        set(value) {
            obj = value
        }

    fun filter(filters: FiltersController): ArrayList<Game> {
        return games.filter {
            filters.checkGame(it)
        } as ArrayList<Game>
    }

    fun add(game: Game) {
        games.add(game)
        notifyChange()
    }

    fun reset() {
        games.clear()
        notifyChange(true)
    }

    fun toJson(): String {
        val json = Json(JsonConfiguration.Stable)
        return json.stringify(Game.serializer().list, games)
    }

    fun fromJson(json: String) {
        val js = Json(JsonConfiguration.Stable)
        games = js.parse(Game.serializer().list, json) as ArrayList<Game>
    }
}