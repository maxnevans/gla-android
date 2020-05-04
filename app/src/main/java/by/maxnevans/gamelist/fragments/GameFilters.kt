package by.maxnevans.gamelist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.navigation.Navigation
import by.maxnevans.gamelist.R
import by.maxnevans.gamelist.model.dao.Filters
import by.maxnevans.gamelist.model.FiltersBuilder
import by.maxnevans.gamelist.model.Storage
import by.maxnevans.gamelist.view.UIAdapter

/**
 * A simple [Fragment] subclass.
 */
class GameFilters : Fragment() {
    var maxCost: SeekBar? = null
    var minCost: SeekBar? = null
    var maxRating: SeekBar? = null
    var minRating: SeekBar? = null
    var maxCountPlayers: SeekBar? = null
    var minCountPlayers: SeekBar? = null
    var txtCost: TextView? = null
    var txtRating: TextView? = null
    var txtCountPlayers: TextView? = null
    var txtValCost: TextView? = null
    var txtValRating: TextView? = null
    var txtValCountPlayers: TextView? = null
    var txtNavText: TextView? = null
    var btnApplyFilters: Button? = null
    var btnClearFilters: Button? = null
    var filters: Filters = Storage.filters.raw.copy()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_filters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUIElements(view)
        setupInitialValues()
        setupSeekbarValueChangeListeners()
        setupClickListeners()
    }

    private fun setupInitialValues() {
        writeFiltersToUI(Storage.filters.raw.copy())
        filters = Storage.filters.raw.copy()
    }

    private fun setupUIElements(view: View) {
        maxCost = view.findViewById(R.id.game_filters_sb_max_cost)
        minCost = view.findViewById(R.id.game_filters_sb_min_cost)
        maxCountPlayers = view.findViewById(R.id.game_filters_sb_max_count_players)
        minCountPlayers = view.findViewById(R.id.game_filters_sb_min_count_players)
        maxRating = view.findViewById(R.id.game_filters_sb_max_rating)
        minRating = view.findViewById(R.id.game_filters_sb_min_rating)

        txtCost = view.findViewById(R.id.game_filters_txt_cost)
        txtRating = view.findViewById(R.id.game_filters_txt_rating)
        txtCountPlayers = view.findViewById(R.id.game_filters_txt_count_players)
        txtValCost = view.findViewById(R.id.game_filters_txt_val_cost)
        txtValRating = view.findViewById(R.id.game_filters_txt_val_rating)
        txtValCountPlayers = view.findViewById(R.id.game_filters_txt_val_count_players)

        txtNavText = view.findViewById(R.id.game_filters_txt_nav_text)

        btnApplyFilters = view.findViewById(R.id.game_filters_btn_apply_filters)
        btnClearFilters = view.findViewById(R.id.game_filters_btn_clear_filters)

        UIAdapter.setFont(txtCost!!, Storage.settings.raw)
        UIAdapter.setFont(txtValCost!!, Storage.settings.raw)
        UIAdapter.setFont(txtRating!!, Storage.settings.raw)
        UIAdapter.setFont(txtValRating!!, Storage.settings.raw)
        UIAdapter.setFont(txtCountPlayers!!, Storage.settings.raw)
        UIAdapter.setFont(txtValCountPlayers!!, Storage.settings.raw)
        UIAdapter.setFont(txtNavText!!, Storage.settings.raw)
        UIAdapter.setFont(btnApplyFilters!!, Storage.settings.raw)
        UIAdapter.setFont(btnClearFilters!!, Storage.settings.raw)
    }

    private fun setupClickListeners() {
        view?.findViewById<ImageButton>(R.id.game_filters_img_btn_back)?.setOnClickListener() { onBackClick(it) }
        btnApplyFilters?.setOnClickListener() { onApplyFilters(it) }
        btnClearFilters?.setOnClickListener() { onClearFilters(it) }
    }

    private fun onBackClick(it: View) {
        Navigation.findNavController(it).popBackStack()
    }

    private fun onApplyFilters(it: View) {
        Storage.filters.raw = filters.copy()
        onBackClick(it)
    }

    private fun onClearFilters(it: View) {
        filters = FiltersBuilder.buildDefault()
        writeFiltersToUI(filters)
    }

    private fun setupSeekbarValueChangeListeners() {
        setSeekBarListener(minCost!!) { sb -> onMinCostChangeValue(filters, sb)}
        setSeekBarListener(maxCost!!) { sb -> onMaxCostChangeValue(filters, sb)}
        setSeekBarListener(minRating!!) { sb -> onMinRatingChangeValue(filters, sb)}
        setSeekBarListener(maxRating!!) { sb -> onMaxRatingChangeValue(filters, sb)}
        setSeekBarListener(minCountPlayers!!) { sb -> onMinCountPlayersChangeValue(filters, sb)}
        setSeekBarListener(maxCountPlayers!!) { sb -> onMaxCountPlayersChangeValue(filters, sb)}
    }

    private fun setSeekBarListener(sb: SeekBar, cb: (sb: SeekBar) -> Unit) {
        sb.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) { cb(seekBar) }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun onMinCostChangeValue(filters: Filters, sb: SeekBar) {
        filters.minCost = UIAdapter.getCost(sb)
        UIAdapter.setCostRange(filters.minCost, filters.maxCost, txtValCost!!)
    }

    private fun onMaxCostChangeValue(filters: Filters, sb: SeekBar) {
        filters.maxCost = UIAdapter.getCost(sb)
        UIAdapter.setCostRange(filters.minCost, filters.maxCost, txtValCost!!)
    }

    private fun onMinCountPlayersChangeValue(filters: Filters, sb: SeekBar) {
        filters.minCountPlayers = UIAdapter.getCountPlayers(sb)
        UIAdapter.setCountPlayersRange(filters.minCountPlayers, filters.maxCountPlayers, txtValCountPlayers!!)
    }

    private fun onMaxCountPlayersChangeValue(filters: Filters, sb: SeekBar) {
        filters.maxCountPlayers = UIAdapter.getCountPlayers(sb)
        UIAdapter.setCountPlayersRange(filters.minCountPlayers, filters.maxCountPlayers, txtValCountPlayers!!)
    }

    private fun onMinRatingChangeValue(filters: Filters, sb: SeekBar) {
        filters.minRating = UIAdapter.getRating(sb)
        UIAdapter.setRatingRange(filters.minRating, filters.maxRating, txtValRating!!)
    }

    private fun onMaxRatingChangeValue(filters: Filters, sb: SeekBar) {
        filters.maxRating = UIAdapter.getRating(sb)
        UIAdapter.setRatingRange(filters.minRating, filters.maxRating, txtValRating!!)
    }

    private fun writeFiltersToUI(filters: Filters) {
        UIAdapter.setCost(filters.minCost, minCost!!)
        UIAdapter.setCost(filters.maxCost, maxCost!!)
        UIAdapter.setCostRange(filters.minCost, filters.maxCost, txtValCost!!)
        UIAdapter.setRating(filters.minRating, minRating!!)
        UIAdapter.setRating(filters.maxRating, maxRating!!)
        UIAdapter.setCostRange(filters.minRating, filters.maxRating, txtValRating!!)
        UIAdapter.setCountPlayers(filters.minCountPlayers, minCountPlayers!!)
        UIAdapter.setCountPlayers(filters.maxCountPlayers, maxCountPlayers!!)
        UIAdapter.setCountPlayersRange(filters.minCountPlayers, filters.maxCountPlayers, txtValCountPlayers!!)
    }
}
