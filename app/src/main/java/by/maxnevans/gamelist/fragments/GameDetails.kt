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
import by.maxnevans.gamelist.model.dao.Game
import by.maxnevans.gamelist.model.Storage
import by.maxnevans.gamelist.view.UIAdapter
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

/**
 * A simple [Fragment] subclass.
 */
class GameDetails : Fragment() {
    private var txtValName: TextView? = null
    private var txtWatchTrailer: TextView? = null
    private var txtCountPlayers: TextView? = null
    private var txtValCountPlayers: TextView? = null
    private var txtRating: TextView? = null
    private var txtValRating: TextView? = null
    private var txtCost: TextView? = null
    private var txtValCost: TextView? = null
    private var txtValDescription: TextView? = null
    private var txtNavText: TextView? = null
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
        setupUIElements(view)
        setupInitialValues(view)
    }

    private fun setupUIElements(view: View) {
        txtValName = view.findViewById(R.id.game_details_txt_val_name)
        txtWatchTrailer = view.findViewById(R.id.game_details_txt_watch_trailer)
        txtCountPlayers = view.findViewById(R.id.game_details_txt_count_players)
        txtValCountPlayers = view.findViewById(R.id.game_details_txt_val_count_players)
        txtRating = view.findViewById(R.id.game_details_txt_rating)
        txtValRating = view.findViewById(R.id.game_details_txt_val_rating)
        txtCost = view.findViewById(R.id.game_details_txt_cost)
        txtValCost = view.findViewById(R.id.game_details_txt_val_cost)
        txtValDescription = view.findViewById(R.id.game_details_txt_val_description)
        txtNavText = view.findViewById(R.id.game_details_txt_nav_text)

        UIAdapter.setFont(txtValName!!, Storage.settings.raw)
        UIAdapter.setFont(txtWatchTrailer!!, Storage.settings.raw)
        UIAdapter.setFont(txtCountPlayers!!, Storage.settings.raw)
        UIAdapter.setFont(txtValCountPlayers!!, Storage.settings.raw)
        UIAdapter.setFont(txtRating!!, Storage.settings.raw)
        UIAdapter.setFont(txtValRating!!, Storage.settings.raw)
        UIAdapter.setFont(txtCost!!, Storage.settings.raw)
        UIAdapter.setFont(txtValCost!!, Storage.settings.raw)
        UIAdapter.setFont(txtValDescription!!, Storage.settings.raw)
        UIAdapter.setFont(txtNavText!!, Storage.settings.raw)
    }

    private fun setupInitialValues(view: View) {
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
            val dialog = builder.show()
            UIAdapter.setFont(dialog.findViewById<TextView>(android.R.id.message), Storage.settings.raw)
            //UIAdapter.setFont(dialog.findViewById<TextView>(androidx.appcompat.R.id.alertTitle), Storage.settings.raw)
            UIAdapter.setFont(dialog.getButton(AlertDialog.BUTTON_POSITIVE), Storage.settings.raw)
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
