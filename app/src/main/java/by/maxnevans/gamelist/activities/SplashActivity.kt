package by.maxnevans.gamelist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import by.maxnevans.gamelist.R
import by.maxnevans.gamelist.model.dao.Game
import by.maxnevans.gamelist.model.Storage
import by.maxnevans.gamelist.view.Fonts
import by.maxnevans.gamelist.view.Formatter

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        // TODO: load games from database
        val gDota2 = Game(0, "Dota 2 ", 0.0, 4.3, 10000.0, "", null, "android.resource://" + baseContext.packageName + "/" + R.raw.dota2)
        val gLol = Game(1, "League of Legends", 0.0, 4.3, 10000.0, "", null, null)
        Storage.games.beginUpdateTransaction()
        Storage.games.add(gDota2)
        Storage.games.add(gLol)
        Storage.games.endUpdateTransaction()

        Fonts.loadFonts(baseContext)
        Formatter.setupResourceSource(baseContext)

        Handler().postDelayed(Runnable {
            val i = Intent(baseContext, MainActivity::class.java)
            startActivity(i)
            finish()
        }, 500)
    }
}
