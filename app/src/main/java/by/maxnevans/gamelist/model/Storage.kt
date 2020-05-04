package by.maxnevans.gamelist.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import by.maxnevans.gamelist.R
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.nio.file.Paths

object Storage {
    private var settingsController = SettingsController()
    private var mediaController = FilesController()
    private var filtersController = FiltersController()
    private var gamesController = GamesController()
    private var db: FirebaseFirestore? = null
    private var storage: FirebaseStorage? = null
    private var gamesDatabaseRef: CollectionReference? = null
    private var gamesLocalRef: File? = null
    private var context: Context? = null

    val DEFAULT_GAMES_FILENAME = "games.json"
    val DEFAULT_GAMES_COLLECTION = "games"

    val settings: SettingsController
        get() {
            return settingsController
        }

    val filters: FiltersController
        get() {
            return filtersController
        }

    val games: GamesController
        get() {
            return gamesController
        }

    private val media: FilesController
        get() {
            return mediaController
        }

    fun resolveImage(context: Context, logo: String?, cb: (Bitmap?) -> Unit) {
        cb(BitmapFactory.decodeResource(context.resources, R.drawable.default_logo))

        media ?: return
        if (logo == null || logo.isBlank()) return

        val logoPath = media.createPathToLocalStorage(logo)
        if (logoPath != null)
            return cb(BitmapFactory.decodeFile(logoPath))


        media.loadFromDatabase(logo){
            if (it != null) {
                media.saveToLocalStorage(logo, it) {success ->
                    if (success)
                        cb(BitmapFactory.decodeFile(media.createPathToLocalStorage(logo)))
                }
            }
        }
    }

    fun resolveTrailer(context: Context, trailer: String?, cb: (String?) -> Unit) {
        trailer ?: return cb(null)
        if (trailer.isBlank()) return cb(null)

        val local = media.createPathToLocalStorage(trailer)
        if (local != null)
            return cb(local)

        media.createUrlToDatabase(trailer){ url ->
            if (url !=null)
                cb(url)
            else
                cb(null)
        }

        media.loadFromDatabase(trailer){
            if (it != null) {
                media.saveToLocalStorage(trailer, it){successSave ->
                    if (successSave)
                        cb(media.createPathToLocalStorage(trailer))
                }
            }
        }
    }

    fun setup(context: Context) {
        db = FirebaseFirestore.getInstance()
        gamesDatabaseRef = db?.collection(DEFAULT_GAMES_COLLECTION)
        storage = FirebaseStorage.getInstance()
        gamesLocalRef = File(context.filesDir, DEFAULT_GAMES_FILENAME)
        this.context = context

        games.setupSources(gamesDatabaseRef!!, gamesLocalRef!!)
        val mediaFilesDir = File(context.cacheDir, "media")
        mediaFilesDir.mkdir()
        media.setupSources(storage!!.reference, mediaFilesDir)
    }

    fun loadFromDatabase(onLoadCb: (Boolean)-> Unit = {}) {
        gamesController.loadFromDatabase(onLoadCb)
    }

    fun loadFromLocalStorage(onLoadCb: (Boolean)-> Unit = {}) {
        gamesController.loadFromLocalStorage(onLoadCb)
    }

    fun saveToDatabase(onSaveCb: (Boolean) -> Unit = {}) {
        gamesController.saveToDatabase(onSaveCb)
    }

    fun saveToLocalStorage(onSaveCb: (Boolean) -> Unit = {}) {
        gamesController.saveToLocalStorage(onSaveCb)
    }
}