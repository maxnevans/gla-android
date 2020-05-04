package by.maxnevans.gamelist.model

import android.util.Log
import by.maxnevans.gamelist.model.dao.Game
import com.google.firebase.firestore.CollectionReference
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.io.File

class GamesController internal constructor(): UpdatableController<ArrayList<Game>>() {
    override var obj: ArrayList<Game> = arrayListOf()
    private var gamesDatabaseRef: CollectionReference? = null
    private var gamesLocalRef: File? = null

    private var games: ArrayList<Game>
        get() {
            return obj
        }
        set(value) {
            obj = value
            notifyChange()
        }

    fun filter(filters: FiltersController): ArrayList<Game> {
        return games.filter {
            filters.checkGame(it)
        } as ArrayList<Game>
    }

    fun add(game: Game) {
        gamesDatabaseRef ?: return
        gamesLocalRef ?: return

        games.add(game)
        saveGamesToDatabase(gamesDatabaseRef!!, arrayListOf(game))
        saveGamesToLocalStorage(gamesLocalRef!!, games)
        notifyChange()
    }

    fun reset() {
        games.clear()
        notifyChange()
    }

    fun toJson(games: ArrayList<Game>): String {
        val json = Json(JsonConfiguration.Stable)
        return json.stringify(Game.serializer().list, games)
    }

    fun fromJson(json: String): ArrayList<Game> {
        val js = Json(JsonConfiguration.Stable)
        return js.parse(Game.serializer().list, json) as ArrayList<Game>
    }

    fun loadFromDatabase(cb: (Boolean) -> Unit = {}) {
        gamesDatabaseRef ?: return

        loadGamesFromDatabase(gamesDatabaseRef!!){
            games = it
            cb(true)
        }
    }

    fun saveToDatabase(cb: (Boolean) -> Unit = {}) {
        gamesDatabaseRef ?: return

        saveGamesToDatabase(gamesDatabaseRef!!, games)
        cb(true)
    }

    fun loadFromLocalStorage(cb: (Boolean) -> Unit = {}) {
        gamesLocalRef ?: return

        loadGamesFromLocalStorage(gamesLocalRef!!){
            if (it == null)
                cb(false)
            else {
                games = it
                cb(true)
            }
        }
    }

    fun saveToLocalStorage(cb: (Boolean) -> Unit = {}) {
        gamesLocalRef ?: return

        saveGamesToLocalStorage(gamesLocalRef!!, games)
        cb(true)
    }

    fun setupSources(databaseRef: CollectionReference, localRef: File) {
        gamesDatabaseRef = databaseRef
        gamesLocalRef = localRef
    }

    private fun loadGamesFromDatabase(gamesRef: CollectionReference, cb: (ArrayList<Game>) -> Unit) {
        gamesRef.get().addOnSuccessListener { snapshot ->
            val games = arrayListOf<Game>()

            for (document in snapshot.documents) {
                val game = document.data
                val g: Game = GamesBuilder.buildDefault()
                g.name = game?.get("name") as? String ?: ""
                g.cost = game?.get("cost") as? Double ?: 0.0
                g.countPlayers = game?.get("countPlayers") as? Double ?: 0.0
                g.rating = game?.get("rating") as? Double ?: 0.0
                g.description = game?.get("description") as? String ?: ""
                g.logo = game?.get("logo") as? String
                g.trailer = game?.get("trailer") as? String

                games.add(g)
            }
            cb(games)
        }
    }

    private fun saveGamesToDatabase(gamesRef: CollectionReference, games: ArrayList<Game>) {
        for (game in games)
            gamesRef.document().set(game)
    }

    private fun loadGamesFromLocalStorage(file: File, cb: (ArrayList<Game>?) -> Unit) {
        if (!file.exists()) return cb(null)
        val jsonString = file.readText()
        cb(this.fromJson(jsonString))
    }

    private fun saveGamesToLocalStorage(gamesLocalRef: File, games: ArrayList<Game>) {
        gamesLocalRef.writeText(this.toJson(games))
    }
}