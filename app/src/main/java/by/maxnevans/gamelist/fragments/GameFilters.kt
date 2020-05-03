package by.maxnevans.gamelist.fragments

import android.app.usage.StorageStats
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
import by.maxnevans.gamelist.dao.Filters
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
    var filters: Filters = Storage.filters.raw

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
    }

    override fun onStart() {
        super.onStart()
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
        txtCost = view.findViewById(R.id.game_filters_txt_val_cost)
        txtRating = view.findViewById(R.id.game_filters_txt_val_rating)
        txtCountPlayers = view.findViewById(R.id.game_filters_txt_val_count_players)
    }

    private fun setupClickListeners() {
        view?.findViewById<ImageButton>(R.id.game_filters_img_btn_back)?.setOnClickListener() { onBackClick(it) }
        view?.findViewById<Button>(R.id.game_filters_btn_apply_filters)?.setOnClickListener() { onApplyFilters(it) }
        view?.findViewById<Button>(R.id.game_filters_btn_clear_filters)?.setOnClickListener() { onClearFilters(it) }
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
        UIAdapter.setCostRange(filters.minCost, filters.maxCost, txtCost!!)
    }

    private fun onMaxCostChangeValue(filters: Filters, sb: SeekBar) {
        filters.maxCost = UIAdapter.getCost(sb)
        UIAdapter.setCostRange(filters.minCost, filters.maxCost, txtCost!!)
    }

    private fun onMinCountPlayersChangeValue(filters: Filters, sb: SeekBar) {
        filters.minCountPlayers = UIAdapter.getCountPlayers(sb)
        UIAdapter.setCountPlayersRange(filters.minCountPlayers, filters.maxCountPlayers, txtCountPlayers!!)
    }

    private fun onMaxCountPlayersChangeValue(filters: Filters, sb: SeekBar) {
        filters.maxCountPlayers = UIAdapter.getCountPlayers(sb)
        UIAdapter.setCountPlayersRange(filters.minCountPlayers, filters.maxCountPlayers, txtCountPlayers!!)
    }

    private fun onMinRatingChangeValue(filters: Filters, sb: SeekBar) {
        filters.minRating = UIAdapter.getRating(sb)
        UIAdapter.setRatingRange(filters.minRating, filters.maxRating, txtRating!!)
    }

    private fun onMaxRatingChangeValue(filters: Filters, sb: SeekBar) {
        filters.maxRating = UIAdapter.getRating(sb)
        UIAdapter.setRatingRange(filters.minRating, filters.maxRating, txtRating!!)
    }

    private fun writeFiltersToUI(filters: Filters) {
        UIAdapter.setCost(filters.minCost, minCost!!)
        UIAdapter.setCost(filters.maxCost, maxCost!!)
        UIAdapter.setCostRange(filters.minCost, filters.maxCost, txtCost!!)
        UIAdapter.setRating(filters.minRating, minRating!!)
        UIAdapter.setRating(filters.maxRating, maxRating!!)
        UIAdapter.setCostRange(filters.minRating, filters.maxRating, txtRating!!)
        UIAdapter.setCountPlayers(filters.minCountPlayers, minCountPlayers!!)
        UIAdapter.setCountPlayers(filters.maxCountPlayers, maxCountPlayers!!)
        UIAdapter.setCountPlayersRange(filters.minCountPlayers, filters.maxCountPlayers, txtCountPlayers!!)
    }
}
