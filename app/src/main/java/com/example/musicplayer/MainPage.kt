package com.example.musicplayer

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class MainPage : AppCompatActivity() {
    private val constantText = "\n One Stop\n For\n"
    private val changingTextArray = arrayOf("Listening\n Music!!", "YT Streaming!!", "Downloading\n Songs!!")
    private var currentIndex = 0
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        val flowHere = findViewById<TextView>(R.id.flowHere)
        val startListeningButton = findViewById<Button>(R.id.startListeningButton)
        // Set up TranslateAnimation for the changing text (from top to bottom)
        val translateAnimationText = TranslateAnimation(0f, 0f, -800f, 0f) // Adjust the starting position
        translateAnimationText.duration = 1000

        // Set up AlphaAnimation for fading the changing text
        val alphaAnimationChangingText = AlphaAnimation(0f, 1f)
        alphaAnimationChangingText.duration = 1000

        // Combine animations with AnimationSet for the TextView
        val animationSetText = AnimationSet(true)
        animationSetText.addAnimation(translateAnimationText)
        animationSetText.addAnimation(alphaAnimationChangingText)

        // Apply the alpha animation only to the changing text
        val startAlphaOffset = 0f // Change this value based on when you want the alpha animation to start
        alphaAnimationChangingText.startOffset = (startAlphaOffset * 1000).toLong()

        // Apply the animation set to the TextView
        flowHere.startAnimation(animationSetText)

        // Set up TranslateAnimation for the button (from bottom to original position)
        val translateAnimationButton = TranslateAnimation(0f, 0f, 800f, 0f)
        translateAnimationButton.duration = 1000

        // Combine animations with AnimationSet for the Button
        val animationSetButton = AnimationSet(true)
        animationSetButton.addAnimation(translateAnimationButton)

        // Apply the animation set to the Button
        startListeningButton.startAnimation(animationSetButton)

        // Start the cursor blinking animation again
        handler.postDelayed({
            changeTextWithAnimation(flowHere)
        }, 2000)
        startListeningButton.setOnClickListener {
            // Initiate the size-decreasing animation when the button is clicked
            initiateSizeDecreaseAnimation(flowHere, startListeningButton)
        }

        // Start the cursor blinking animation initially
        handler.postDelayed({
            changeTextWithAnimation(flowHere)
        }, 2000)
    }

    private fun initiateSizeDecreaseAnimation(textView: TextView, button: Button) {
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()

        // Calculate the middle position
        val middlePosition = screenHeight / 2f

        // Calculate the scale factor for the TextView and Button to reach the middle position
        val scaleText = middlePosition / 950
        val scaleButton = middlePosition / button.y

        // Set up ViewPropertyAnimator for TextView
        textView.animate()
            .scaleY(scaleText)
            .alpha(0f)
            .setDuration(1000)
            .setInterpolator(DecelerateInterpolator())
            .start()

        // Set up ViewPropertyAnimator for Button
        button.animate()
            .scaleY(scaleButton)
            .setDuration(1000)
            .setInterpolator(DecelerateInterpolator())
            .withEndAction {
                // Start the new activity after the animation
                val intent = Intent(this, MainHome::class.java)
                intent.putExtra(MainHome.EXTRA_CIRCULAR_REVEAL_X, (button.x + button.width / 2).toInt())
                intent.putExtra(MainHome.EXTRA_CIRCULAR_REVEAL_Y, (button.y + button.height / 2).toInt())
                startActivity(intent)
                finish()
            }
            .start()
    }

    private fun changeTextWithAnimation(textView: TextView) {
        // Create a SpannableString to apply different styles to different parts of the text
        val spannableString = SpannableString("$constantText ${changingTextArray[currentIndex]}")

        // Apply a different color to the constant text
        spannableString.setSpan(ForegroundColorSpan(resources.getColor(R.color.colorConstantText)), 0, constantText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Set the SpannableString to the TextView
        textView.text = spannableString

        // Increment the index for the next changing text
        currentIndex = (currentIndex + 1) % changingTextArray.size

        // Start the cursor blinking animation again
        handler.postDelayed({
            changeTextWithAnimation(textView)
        }, 2000)
    }
}
