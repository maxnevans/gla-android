package by.maxnevans.gamelist.view

import android.R
import android.graphics.BitmapFactory
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import by.maxnevans.gamelist.model.FiltersBuilder
import by.maxnevans.gamelist.model.SettingsBuilder
import by.maxnevans.gamelist.model.Storage
import by.maxnevans.gamelist.model.dao.Settings
import org.w3c.dom.Text
import kotlin.math.pow
import kotlin.math.roundToInt

object UIAdapter {
    fun setCostRange(minCost: Double, maxCost: Double, txt: TextView) {
        txt.text = Formatter.formatCostRange(minCost, maxCost)
    }

    fun setCost(cost: Double, sb: SeekBar) {
        sb.progress = getSeekBarProgressFromPercentage(calcCostPercentage(cost), sb)
    }

    fun setCost(cost: Double, txt: TextView) {
        txt.text = Formatter.formatCost(cost)
    }

    fun getCost(sb: SeekBar): Double {
        return calcCost(getSeekBarProgressInProcentages(sb))
    }

    fun setRatingRange(minRating: Double, maxRating: Double, txt: TextView) {
        txt.text = Formatter.formatRatingRange(minRating, maxRating)
    }

    fun setRating(rating: Double, sb: SeekBar) {
        sb.progress = getSeekBarProgressFromPercentage(calcRatingPercentage(rating), sb)
    }

    fun setRating(rating: Double, txt: TextView) {
        txt.text = Formatter.formatRating(rating)
    }

    fun getRating(sb: SeekBar): Double {
        return calcRating(getSeekBarProgressInProcentages(sb))
    }

    fun setCountPlayersRange(minPlayers: Double, maxPlayers: Double, txt: TextView) {
        txt.text = Formatter.formatCountPlayersRange(minPlayers, maxPlayers)
    }

    fun setCountPlayers(countPlayers: Double, sb: SeekBar) {
        sb.progress = getSeekBarProgressFromPercentage(calcCountPlayersPercentage(countPlayers), sb)
    }

    fun setCountPlayers(countPlayers: Double, txt: TextView) {
        txt.text = Formatter.formatCountPlayers(countPlayers)
    }

    fun getCountPlayers(sb: SeekBar): Double {
        return calcCountPlayers(getSeekBarProgressInProcentages(sb))
    }

    fun setLogo(logo: String?, image: ImageView) {
        Storage.resolveImage(image.context, logo){
            image.setImageBitmap(it)
        }
    }

    fun setName(name: String, txt: TextView) {
        txt.text = Formatter.formatName(name)
    }

    fun setDescription(desc: String?, txt: TextView) {
        txt.text = desc
    }

    fun setFontSize(fontSize: Int, sb: SeekBar) {
        sb.progress = getSeekBarProgressFromPercentage(calcFontSizeFromPercentage(fontSize), sb)
    }

    fun setSpinnerValues(sp: Spinner, values: List<String>) {
        val aa = ArrayAdapter<String>(sp.context, R.layout.simple_spinner_item, values)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp.adapter = aa
    }

    fun setSpinnerSelection(sp: Spinner, value: String) {
        sp.setSelection((sp.adapter as ArrayAdapter<String>).getPosition(value))
    }

    fun getFontSize(sb: SeekBar): Int {
        return calcFontSize(getSeekBarProgressInProcentages(sb))
    }

    fun setFont(txt: TextView, settings: Settings) {
        txt.typeface = Fonts.map[settings.fontFamily]
        txt.textSize = settings.fontSize.toFloat()
    }

    private fun getSeekBarProgressInProcentages(sb: SeekBar): Double {
        return sb.progress * 1.0 / sb.max
    }

    private fun getSeekBarProgressFromPercentage(percentage: Double, sb: SeekBar): Int {
        return (percentage * sb.max).roundToInt()
    }

    private fun calcCost(costInProcentages: Double): Double {
        return makeNonLinearFromLinear(costInProcentages) * FiltersBuilder.DIFF_COST + FiltersBuilder.MIN_COST
    }

    private fun calcCountPlayers(countPlayersInProcentages: Double): Double {
        return makeNonLinearFromLinear(countPlayersInProcentages) * FiltersBuilder.DIFF_COUNT_PLAYERS + FiltersBuilder.MIN_COUNT_PLAYERS
    }

    private fun calcRating(ratingInPercentages: Double): Double {
        return ratingInPercentages * FiltersBuilder.DIFF_RATING + FiltersBuilder.MIN_RATING
    }

    private fun calcCostPercentage(cost: Double): Double {
        return makeLinearFromNonLinear((cost - FiltersBuilder.MIN_COST) / FiltersBuilder.DIFF_COST)
    }

    private fun calcRatingPercentage(rating: Double): Double {
        return (rating - FiltersBuilder.MIN_RATING) / FiltersBuilder.DIFF_RATING
    }

    private fun calcCountPlayersPercentage(countPlayers: Double): Double {
        return makeLinearFromNonLinear((countPlayers - FiltersBuilder.MIN_COUNT_PLAYERS) / FiltersBuilder.DIFF_COUNT_PLAYERS)
    }

    private fun calcFontSize(fontSizeInProcentages: Double): Int {
        return (fontSizeInProcentages * SettingsBuilder.DIFF_FONT_SIZE + SettingsBuilder.MIN_FONT_SIZE).roundToInt()
    }

    private fun calcFontSizeFromPercentage(fontSize: Int): Double {
        return (fontSize * 1.0 - SettingsBuilder.MIN_FONT_SIZE) / SettingsBuilder.DIFF_FONT_SIZE
    }

    private fun makeNonLinearFromLinear(linear: Double): Double {
        val alpha = 10000
        val gamma= 10.0
        return alpha * (Math.exp(linear / alpha) - 1)*linear.pow(gamma)
    }

    private fun makeLinearFromNonLinear(nonLinear: Double): Double {
        val alpha = 10000
        val gamma= 10.0
        return Math.log(Math.pow(nonLinear / alpha, 1 / gamma) + 1) * alpha
    }
}