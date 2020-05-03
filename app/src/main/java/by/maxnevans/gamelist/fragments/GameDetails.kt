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
import by.maxnevans.gamelist.view.UIAdapter
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

/**
 * A simple [Fragment] subclass.
 */
class GameDetails : Fragment() {

    private var game: Game? = null

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
        game = json.parse(Game.serializer(), GameDetailsArgs.fromBundle(requireArguments()).jsonGame ?: "")
        fillGameData(view, game!!)
        view.findViewById<ImageButton>(R.id.game_details_img_btn_back).setOnClickListener(){ onBackClick(it) }
    }

    private fun fillGameData(view: View, game: Game) {
        UIAdapter.setName(game.name,  view.findViewById<TextView>(R.id.game_details_txt_val_name))
        UIAdapter.setCost(game.cost, view.findViewById<TextView>(R.id.game_details_txt_val_cost))
        UIAdapter.setRating(game.rating, view.findViewById<TextView>(R.id.game_details_txt_val_rating))
        UIAdapter.setCountPlayers(game.countPlayers, view.findViewById<TextView>(R.id.game_details_txt_val_count_players))
        UIAdapter.setLogo(game.logo, view.findViewById<ImageView>(R.id.game_details_img_logo))
        UIAdapter.setDescription(game.description, view.findViewById<TextView>(R.id.game_details_txt_val_description))
    }

    private fun onWatchTrailerClick(it: View) {
        if (game?.trailer == null) {
            val builder = AlertDialog.Builder(it.context)
            builder.setMessage(R.string.failed_to_play_trailer)
            builder.setTitle(R.string.trailer_not_found)
            builder.setPositiveButton("Ok") { dialog: DialogInterface?, which: Int ->
                // DO noting
            }
            builder.show()
        } else {
            val action = GameDetailsDirections.actionFragmentGameDetailsToActivityTrailerPlayer(Storage.resolveTrailer(requireContext(), game?.trailer))
            Navigation.findNavController(it).navigate(action)
        }
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
