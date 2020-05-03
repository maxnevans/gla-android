package by.maxnevans.gamelist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import by.maxnevans.gamelist.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        // TODO: preform load from database

        Handler().postDelayed(Runnable {
            val i = Intent(baseContext, MainActivity::class.java)
            startActivity(i)
            finish()
        }, 500)
    }
}
