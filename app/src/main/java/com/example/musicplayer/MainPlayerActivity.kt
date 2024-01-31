package com.example.musicplayer


import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ActivityMainPlayerBinding // Import your generated binding class

class MainPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainPlayerBinding // Use your generated binding class
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var handler: Handler
    private var isSeeking = false

    @RequiresApi(34)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        val intent = intent

        val songName = intent.getStringExtra("songname")
        val songDesc = intent.getStringExtra("songdesc")

        binding.playerSongName.text = songName.toString()
        binding.playerSongDesc.text = songDesc.toString()

        // Initialize MediaPlayer with the song to play
        mediaPlayer = MediaPlayer.create(this, R.raw.music)

        // Initialize Handler
        handler = Handler()

        // Set max value of SeekBar to the total duration of the song
        binding.musicProgress.max = mediaPlayer.duration

        // Start updating SeekBar progress
        updateSeekBarProgress()

        mediaPlayer.start() // Start playing the song

        binding.playPauseBtn.setOnClickListener {
            togglePlayPause() // Call the function to toggle play/pause
        }

        // Set up SeekBar listener
        binding.musicProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isSeeking = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                isSeeking = false
            }
        })
    }

    // Function to start updating SeekBar progress
    private fun updateSeekBarProgress() {
        // Remove any existing callbacks to prevent duplication
        handler.removeCallbacks(updateSeekBarRunnable)
        // Post the Runnable to update SeekBar progress
        handler.postDelayed(updateSeekBarRunnable, 1000) // 1000 milliseconds = 1 second
    }

    // Runnable to update SeekBar progress
    private val updateSeekBarRunnable = object : Runnable {
        override fun run() {
            if (!isSeeking) {
                // Update SeekBar progress
                binding.musicProgress.progress = mediaPlayer.currentPosition
            }
            // Run this Runnable again after a delay
            handler.postDelayed(this, 1000) // 1000 milliseconds = 1 second
        }
    }

    // Function to toggle play/pause
    private fun togglePlayPause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        } else {
            mediaPlayer.start()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
