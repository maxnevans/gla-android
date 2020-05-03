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
class ApplicationSettings : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_application_settings, container, false)
    }

    override fun onStart() {
        super.onStart()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        view?.findViewById<ImageButton>(R.id.app_settings_img_btn_back)?.setOnClickListener() { onBackClick(it) }
        view?.findViewById<Button>(R.id.app_settings_btn_apply_settings)?.setOnClickListener() { onApplySettingsClick(it) }
    }

    private fun onBackClick(it: View) {
        Navigation.findNavController(it).popBackStack()
    }

    private fun onApplySettingsClick(it: View) {
        // TODO
        onBackClick(it)
    }

}
