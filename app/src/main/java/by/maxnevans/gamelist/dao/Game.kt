package by.maxnevans.gamelist.dao

data class Game(var id: Int,
                var name: String,
                var countPlayers: Double,
                var rating: Double,
                var cost: Double,
                var description: String,
                var logo: String?,
                var trailer: String?)