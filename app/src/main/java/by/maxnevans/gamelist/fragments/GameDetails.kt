package by.maxnevans.gamelist.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.Navigation
import by.maxnevans.gamelist.R

/**
 * A simple [Fragment] subclass.
 */
class GameDetails : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_details, container, false)

        view.findViewById<ImageButton>(R.id.game_details_img_btn_back).setOnClickListener(){ onBackClick(it) }

        return view
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
