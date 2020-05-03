package by.maxnevans.gamelist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.Navigation
import by.maxnevans.gamelist.R

/**
 * A simple [Fragment] subclass.
 */
class AddGame : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_game, container, false)
    }

    override fun onStart() {
        super.onStart()
        setupClickListeners()
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
