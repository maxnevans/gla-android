package by.maxnevans.gamelist.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.MediaController
import android.widget.VideoView
import androidx.constraintlayout.widget.ConstraintLayout
import by.maxnevans.gamelist.R

class TrailerPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_trailer_player)
        val url = intent.getStringExtra("videoUrl")

        val videoPlayer = findViewById<VideoView>(R.id.trailer_player_video_trailer)

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoPlayer)
        videoPlayer.setMediaController(mediaController)

        videoPlayer.setVideoURI(Uri.parse(url))
        videoPlayer.requestFocus()
        videoPlayer.start()
    }
}
