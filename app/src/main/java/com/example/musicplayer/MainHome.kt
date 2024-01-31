package com.example.musicplayer

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainHome : AppCompatActivity() {
    lateinit var vp2:ViewPager2
    lateinit var tl:TabLayout
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        vp2=findViewById(R.id.vp2)
        tl=findViewById(R.id.tl)

        vp2.isUserInputEnabled= false

        val navOpt= arrayOf("Home","Youtube","Playlist")

        val adp = navAdapter(this)
        vp2.adapter = adp
        val whiteIconColor = ColorStateList.valueOf(ContextCompat.getColor(this, android.R.color.white))

        TabLayoutMediator(tl, vp2) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.baseline_home_24)
                }
                1 -> {
                    tab.setIcon(R.drawable.baseline_smart_yt)
                }
                2 -> {
                    tab.setIcon(R.drawable.baseline_playlist_play_24)
                }
            }
            tab.icon?.setTintList(whiteIconColor)

        }.attach()



        vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // Reset the icon color to white for all tabs
                for (i in 0 until tl.tabCount) {
                    val tab = tl.getTabAt(i)
                    tab?.setIcon(when (i) {
                        0 -> R.drawable.baseline_home_24
                        1 -> R.drawable.baseline_smart_yt
                        2 -> R.drawable.baseline_playlist_play_24
                        else -> 0  // You may want to handle additional tabs if present
                    })
                    tab?.icon?.setTintList(whiteIconColor)
                }
            }
        })




        // Check if the activity is started with the circular reveal data
        if (intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) && intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            val revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0)
            val revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0)

            // Wait for the layout to be inflated and view attached before starting the animation
            val view = findViewById<View>(R.id.mainHomeLayout)
            view.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    view.viewTreeObserver.removeOnPreDrawListener(this)

                    // Start the circular reveal animation
                    startCircularRevealAnimation(revealX, revealY)

                    return true
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startCircularRevealAnimation(revealX: Int, revealY: Int) {
        val view = findViewById<View>(R.id.mainHomeLayout)

        // Get the final radius for the circular reveal
        val finalRadius = view.width.coerceAtLeast(view.height).toFloat()

        // Create the circular reveal animation
        val circularReveal = ViewAnimationUtils.createCircularReveal(view, revealX, revealY, 0f, finalRadius)

        // Set the animation duration
        circularReveal.duration = 500

        // Start the animation
        circularReveal.start()

        // Make the view visible during the animation
        view.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent=Intent(this,MainPage::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X"
        const val EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y"
    }
}
