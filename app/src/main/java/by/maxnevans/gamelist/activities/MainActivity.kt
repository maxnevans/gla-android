package by.maxnevans.gamelist.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import by.maxnevans.gamelist.R
import by.maxnevans.gamelist.model.Storage
import by.maxnevans.gamelist.view.LocaleHelper


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        Storage.settings.onChange {
            LocaleHelper.setLocale(this, it.language.code);
            recreate()
        }
    }
}
