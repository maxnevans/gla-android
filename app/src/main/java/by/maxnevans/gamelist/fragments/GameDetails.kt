package by.maxnevans.gamelist.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import by.maxnevans.gamelist.R
import by.maxnevans.gamelist.dao.Game
import by.maxnevans.gamelist.model.Storage
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

/**
 * A simple [Fragment] subclass.
 */
class GameDetails : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val json = Json(JsonConfiguration.Stable)
        val game = json.parse(Game.serializer(), GameDetailsArgs.fromBundle(requireArguments()).jsonGame ?: "")
        fillGameData(view, game)
        view.findViewById<ImageButton>(R.id.game_details_img_btn_back).setOnClickListener(){ onBackClick(it) }
    }

    private fun fillGameData(view: View, game: Game) {
        view.findViewById<TextView>(R.id.game_details_txt_val_name).text = game.name
        view.findViewById<TextView>(R.id.game_details_txt_val_cost).text = "%.2f".format(game.cost)
        view.findViewById<TextView>(R.id.game_details_txt_val_rating).text = "%.1f".format(game.rating)
        view.findViewById<TextView>(R.id.game_details_txt_val_count_players).text = "%.0f".format(game.countPlayers)
        view.findViewById<ImageView>(R.id.game_details_img_logo).background = Storage.instance.resolveImage(requireContext(), game.logo)
        view.findViewById<TextView>(R.id.game_details_txt_val_description).text = game.description
    }

    private fun onWatchTrailerClick(it: View) {
        val builder = AlertDialog.Builder(it.context)
        builder.setMessage(R.string.failed_to_play_trailer)
        builder.setTitle(R.string.trailer_not_found)
        builder.setPositiveButton("Ok") { dialog: DialogInterface?, which: Int ->
            // DO noting
        }
        builder.show()
    }

    private fun onBackClick(it: View) {
        Navigation.findNavController(it).popBackStack()
    }

    override fun onStart() {
        super.onStart()
        setupClickEventListeners()
    }

    private fun setupClickEventListeners() {
        view?.findViewById<TextView>(R.id.game_details_txt_watch_trailer)?.setOnClickListener() { onWatchTrailerClick(it) }
    }

}
