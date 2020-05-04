package by.maxnevans.gamelist.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.MediaController
import android.widget.VideoView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import by.maxnevans.gamelist.R

class TrailerPlayerActivity : AppCompatActivity() {
    private var videoPlayer: VideoView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_trailer_player)

        val url = intent.getStringExtra("videoUrl")

        videoPlayer = findViewById<VideoView>(R.id.trailer_player_video_trailer)

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoPlayer)
        videoPlayer!!.setMediaController(mediaController)

        videoPlayer!!.setVideoURI(Uri.parse(url))
    }

    override fun onStart() {
        super.onStart()
        videoPlayer!!.requestFocus()
        videoPlayer!!.start()
    }
}
