package by.maxnevans.gamelist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import by.maxnevans.gamelist.R
import by.maxnevans.gamelist.model.dao.Language
import by.maxnevans.gamelist.model.dao.Settings
import by.maxnevans.gamelist.model.SettingsBuilder
import by.maxnevans.gamelist.model.Storage
import by.maxnevans.gamelist.view.Fonts
import by.maxnevans.gamelist.view.UIAdapter

/**
 * A simple [Fragment] subclass.
 */
class ApplicationSettings : Fragment() {
    private var settings: Settings = Storage.settings.raw.copy()
    private var spLanguage: Spinner? = null
    private var spFontFamily: Spinner? = null
    private var sbFontSize: SeekBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_application_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUIElements(view)
        setupInitialValues()
        setupSettingsValuesChangeListeners()
        setupClickListeners()
    }

    private fun setupUIElements(view: View) {
        spLanguage =view.findViewById(R.id.app_settings_spinner_language)
        spFontFamily = view.findViewById(R.id.app_settings_spinner_font_family)
        sbFontSize = view.findViewById(R.id.app_settings_sb_font_size)
    }

    private fun setupInitialValues() {
        settings = Storage.settings.raw.copy()
        writeSettingsToUI(settings)
    }

    private fun setupClickListeners() {
        view?.findViewById<ImageButton>(R.id.app_settings_img_btn_back)?.setOnClickListener() { onBackClick(it) }
        view?.findViewById<Button>(R.id.app_settings_btn_apply_settings)?.setOnClickListener() { onApplySettingsClick(it) }
        view?.findViewById<Button>(R.id.app_settings_btn_restore_settings)?.setOnClickListener() { onRestoreSettingsClick(it) }
    }

    private fun onBackClick(it: View) {
        Navigation.findNavController(it).popBackStack()
    }

    private fun onApplySettingsClick(it: View) {
        Storage.settings.raw = settings.copy()
        onBackClick(it)
    }

    private fun onRestoreSettingsClick(it: View) {
        settings = SettingsBuilder.buildDefault()
        writeSettingsToUI(settings)
    }

    private fun setupSettingsValuesChangeListeners() {
        setSpinnerListener(spLanguage!!){parent, position -> onLanguageChange(parent, position)}
        setSpinnerListener(spFontFamily!!){parent, position -> onFontFamilyChange(parent, position)}
        setSeekBarListener(sbFontSize!!){ it -> onFontSizeChange(it)}
    }

    private fun setSeekBarListener(sb: SeekBar, cb: (sb: SeekBar) -> Unit) {
        sb.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) { cb(seekBar) }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setSpinnerListener(sp: Spinner, cb: (AdapterView<*>?, Int) -> Unit) {
        sp.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                cb(parent, position)
            }
        })
    }

    private fun onFontSizeChange(sb: SeekBar) {
        settings.fontSize = UIAdapter.getFontSize(sb)
    }

    private fun onLanguageChange(parent: AdapterView<*>?, position: Int) {
        val item = parent?.getItemAtPosition(position)
        val language = Language.values().find { language ->  language.nativeName == item.toString()} ?: return
        settings.language = language
    }

    private fun onFontFamilyChange(parent: AdapterView<*>?, position: Int) {
        val item = parent?.getItemAtPosition(position) ?: return
        settings.fontFamily = item.toString()
    }

    private fun writeSettingsToUI(settings: Settings) {
        UIAdapter.setSpinnerValues(spLanguage!!, Language.values().map(){ language ->  language.nativeName})
        UIAdapter.setSpinnerValues(spFontFamily!!, Fonts.values)

        UIAdapter.setSpinnerSelection(spLanguage!!, settings.language.nativeName)
        UIAdapter.setSpinnerSelection(spFontFamily!!, settings.fontFamily)
        UIAdapter.setFontSize(settings.fontSize, sbFontSize!!)
    }
}
