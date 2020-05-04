package by.maxnevans.gamelist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.Navigation
import by.maxnevans.gamelist.R
import by.maxnevans.gamelist.model.Storage
import by.maxnevans.gamelist.view.UIAdapter

/**
 * A simple [Fragment] subclass.
 */
class AddGame : Fragment() {
    private var txtValName: TextView? = null
    private var txtValCost: TextView? = null
    private var txtValCountPlayers: TextView? = null
    private var txtValRating: TextView? = null
    private var txtValDescription: TextView? = null
    private var txtNavText: TextView? = null
    private var btnCreateGame: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUIElements(view)
        setupClickListeners()
    }

    private fun setupUIElements(view: View) {
        txtValName = view.findViewById(R.id.add_game_edt_name)
        txtValCost = view.findViewById(R.id.add_game_edt_cost)
        txtValCountPlayers = view.findViewById(R.id.add_game_edt_count_players)
        txtValRating = view.findViewById(R.id.add_game_edt_rating)
        txtValDescription = view.findViewById(R.id.add_game_edt_description)
        txtNavText = view.findViewById(R.id.add_game_txt_nav_text)
        btnCreateGame = view.findViewById(R.id.add_game_btn_add_game)

        UIAdapter.setFont(txtValName!!, Storage.settings.raw)
        UIAdapter.setFont(txtValCost!!, Storage.settings.raw)
        UIAdapter.setFont(txtValCountPlayers!!, Storage.settings.raw)
        UIAdapter.setFont(txtValRating!!, Storage.settings.raw)
        UIAdapter.setFont(txtValDescription!!, Storage.settings.raw)
        UIAdapter.setFont(txtNavText!!, Storage.settings.raw)
        UIAdapter.setFont(btnCreateGame!!, Storage.settings.raw)
    }

    private fun setupClickListeners() {
        view?.findViewById<ImageButton>(R.id.add_game_img_btn_back)?.setOnClickListener() { onBackClick(it) }
        view?.findViewById<Button>(R.id.add_game_btn_add_game)?.setOnClickListener() { onAddGameClick(it) }
    }

    private fun onBackClick(it: View) {
        Navigation.findNavController(it).popBackStack()
    }

    private fun onAddGameClick(it: View) {
        // TODO
        onBackClick(it)
    }

}
