package by.maxnevans.gamelist.model

import android.util.Log
import com.google.firebase.storage.StorageReference
import java.io.File
import java.net.URL

internal class FilesController internal constructor(): UpdatableController<ArrayList<String>>() {
    override var obj = arrayListOf<String>()
    private var storage: StorageReference? = null
    private var filesDir: File? = null

    private var filenames: ArrayList<String>
        get() {
            return obj
        }
        set(value) {
            obj = value
        }

    fun setupSources(storage: StorageReference, filesDir: File) {
        this.storage = storage
        this.filesDir = filesDir
    }

    fun loadFromDatabase(filename: String, cb: (ByteArray?) -> Unit) {
        storage ?: return
        val limit: Long = 1024*1024*15
        storage!!.child(filename).getBytes(limit)
            .addOnSuccessListener(cb)
            .addOnFailureListener { cb(null) }
    }

    fun saveToLocalStorage(filename: String, data: ByteArray, cb: (Boolean) -> Unit = {}) {
        File(filesDir, filename).writeBytes(data)
        cb(true)
    }

    fun loadFromLocalStorage(filename: String, cb: (ByteArray) -> Unit) {
        cb(File(filesDir, filename).readBytes())
    }

    fun createPathToLocalStorage(filename: String): String? {
        val file = File(filesDir, filename)
        if (!file.exists()) return null
        return file.path
    }

    fun createUrlToDatabase(filename: String, cb: (String?) -> Unit) {
        storage ?: return cb(null)
        storage!!.child(filename).downloadUrl
            .addOnSuccessListener{ cb(it.toString()) }
            .addOnFailureListener { cb(null) }
    }
}