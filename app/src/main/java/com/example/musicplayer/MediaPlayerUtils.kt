package com.example.musicplayer

import android.media.MediaPlayer

object MediaPlayerUtils {
    fun togglePlayPause(mediaPlayer: MediaPlayer) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        } else {
            mediaPlayer.start()
        }
    }
}