package by.maxnevans.gamelist.model

import by.maxnevans.gamelist.model.dao.Game

object GamesBuilder {
    fun buildDefault(): Game {
        return Game(0, "",0.0,0.0,0.0,"",null,null)
    }
}