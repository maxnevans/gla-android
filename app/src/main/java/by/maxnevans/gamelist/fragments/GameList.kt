package by.maxnevans.gamelist.fragments

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import by.maxnevans.gamelist.R
import by.maxnevans.gamelist.dao.Game
import by.maxnevans.gamelist.model.Storage
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlin.math.roundToInt

class GameList : Fragment() {
    var itemGame: Array<View?>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("Test123", "onCreateView")

        return inflater.inflate(R.layout.fragment_game_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        setupClickListeners()

        val gDota2 = Game(0, "Dota 2 ", 0.0, 4.3, 10000.0, "", null,
        "android.resource://" + context?.packageName + "/" + R.raw.dota2)
        val dota2 = createGameItem(gDota2)
        val gLol = Game(1, "League of Legends", 0.0, 4.3, 10000.0, "", null, null)
        val lol = createGameItem(gLol)

        val container = view?.findViewById<LinearLayout>(R.id.ctnr_list_v_layout)
        container?.addView(dota2)
        container?.addView(lol)
    }

    private fun setupClickListeners() {
        view?.findViewById<ImageButton>(R.id.img_btn_filters)?.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_game_list_to_game_filters))
        view?.findViewById<ImageButton>(R.id.img_btn_add)?.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_game_list_to_add_game))
        view?.findViewById<ImageButton>(R.id.img_btn_settings)?.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_game_list_to_application_settings))
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)

    private fun onGameClick(it: View) {
        val game = it?.getTag(R.id.game_object)
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

    private fun createGameItem(game: Game): View {
        val item = LayoutInflater.from(view?.context).inflate(R.layout.game_list_item, null)

        item.setTag(R.id.game_object, game)
        item.setOnClickListener() { onGameClick(it) }

        item.findViewById<TextView>(R.id.txt_val_name).text = game.name

        var costString = ""
        if (game.cost > 0)
            costString = "${game.cost}$"
        else
            costString = "Free to play"

        item.findViewById<TextView>(R.id.txt_val_cost).text = costString

        val countPlayersString = when {
            (game.countPlayers > 1000000) -> "${(game.countPlayers / 1e6).roundToInt()} millions+"
            (game.countPlayers > 1000) -> "${(game.countPlayers / 1e3).roundToInt()} thousands+"
            else -> game.countPlayers.roundToInt()
        }

        item.findViewById<TextView>(R.id.txt_val_count_players).text = countPlayersString.toString()

        item.findViewById<TextView>(R.id.txt_val_rating).text = "${game.rating.format(1)} of 5.0"
        item.findViewById<ImageView>(R.id.img_item_logo).background = Storage.instance.resolveImage(requireContext(), game.logo)

        return item
    }
}
