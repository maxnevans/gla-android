package by.maxnevans.gamelist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import by.maxnevans.gamelist.R
import by.maxnevans.gamelist.model.dao.Game
import by.maxnevans.gamelist.model.Storage
import by.maxnevans.gamelist.view.UIAdapter
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

class GameList : Fragment() {
    private var gamesContainer: LinearLayout? = null
    private var navText: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUIElements(view)
        setupClickListeners(view)

        gamesContainer = view.findViewById(R.id.ctnr_list_v_layout)
        updateGameList(gamesContainer!!)
    }

    private fun setupUIElements(view: View) {
        navText = view.findViewById<TextView>(R.id.game_list_nav_text)

        UIAdapter.setFont(navText!!, Storage.settings.raw)
    }

    private fun setupClickListeners(view: View) {
        view.findViewById<ImageButton>(R.id.img_btn_filters)?.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_game_list_to_game_filters))
        view.findViewById<ImageButton>(R.id.img_btn_add)?.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_game_list_to_add_game))
        view.findViewById<ImageButton>(R.id.img_btn_settings)?.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_game_list_to_application_settings))
    }

    private fun onGameClick(it: View) {
        val game = it.getTag(R.id.game_object)
        val json = Json(JsonConfiguration.Stable)
        val jsonGame = json.stringify(Game.serializer(), game as Game)

        val action = GameListDirections.actionGameListToGameDetails(jsonGame)
        Navigation.findNavController(it).navigate(action)
    }

    private fun onFiltersClick(it: View) {
        Navigation.findNavController(it).navigate(R.id.action_game_list_to_game_filters)
    }

    private fun onAddGameClick(it: View) {
        Navigation.findNavController(it).navigate(R.id.action_game_list_to_add_game)
    }

    private fun onSettingsClick(it: View) {
        Navigation.findNavController(it).navigate(R.id.action_game_list_to_application_settings)
    }

    private fun updateGameList(gamesContainer: LinearLayout) {
        for (game in Storage.games.filter(Storage.filters))
            gamesContainer.addView(createGameView(game))
    }

    private fun createGameView(game: Game): View {
        val item = LayoutInflater.from(view?.context).inflate(R.layout.game_list_item, null)

        item.setTag(R.id.game_object, game)
        item.setOnClickListener() { onGameClick(it) }

        val txtValName = item.findViewById<TextView>(R.id.txt_val_name)
        val txtCost = item.findViewById<TextView>(R.id.txt_cost)
        val txtValCost = item.findViewById<TextView>(R.id.txt_val_cost)
        val txtRating = item.findViewById<TextView>(R.id.txt_rating)
        val txtValRating = item.findViewById<TextView>(R.id.txt_val_rating)
        val txtCountPlayers = item.findViewById<TextView>(R.id.txt_count_players)
        val txtValCountPlayers = item.findViewById<TextView>(R.id.txt_val_count_players)

        UIAdapter.setFont(txtValName, Storage.settings.raw)
        UIAdapter.setFont(txtCost, Storage.settings.raw)
        UIAdapter.setFont(txtValCost, Storage.settings.raw)
        UIAdapter.setFont(txtRating, Storage.settings.raw)
        UIAdapter.setFont(txtValRating, Storage.settings.raw)
        UIAdapter.setFont(txtCountPlayers, Storage.settings.raw)
        UIAdapter.setFont(txtValCountPlayers, Storage.settings.raw)

        UIAdapter.setName(game.name, txtValName)
        UIAdapter.setCost(game.cost, txtValCost)
        UIAdapter.setRating(game.rating, txtValRating)
        UIAdapter.setCountPlayers(game.countPlayers, txtValCountPlayers)
        UIAdapter.setLogo(game.logo, item.findViewById<ImageView>(R.id.img_item_logo))

        return item
    }
}
