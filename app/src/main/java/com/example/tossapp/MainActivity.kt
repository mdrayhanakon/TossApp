package com.example.tossapp

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var coinImageView: ImageView
    private lateinit var tossButton: Button
    private lateinit var headsCountTextView: TextView
    private lateinit var tailsCountTextView: TextView
    private lateinit var titleTextView: TextView

    private var headsCount = 0
    private var tailsCount = 0
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        coinImageView = findViewById(R.id.coinImageView)
        tossButton = findViewById(R.id.tossButton)
        headsCountTextView = findViewById(R.id.headsCountTextView)
        tailsCountTextView = findViewById(R.id.tailsCountTextView)
        titleTextView = findViewById(R.id.titleTextView)

        // Initialize sound
        mediaPlayer = MediaPlayer.create(this, R.raw.coin_flip)

        tossButton.setOnClickListener {
            tossCoin()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun tossCoin() {
        // Play sound
        mediaPlayer.start()

        // Start animation
        val flipAnimator = ObjectAnimator.ofFloat(coinImageView, "rotationY", 0f, 1080f)
        flipAnimator.duration = 1000
        flipAnimator.start()

        // Randomly choose heads or tails after the animation
        flipAnimator.addUpdateListener { animation ->
            if (animation.animatedFraction == 1.0f) {
                val result = Random.nextInt(2) // 0 for heads, 1 for tails

                if (result == 0) {
                    coinImageView.setImageResource(R.drawable.heads)
                    headsCount++
                    headsCountTextView.text = "Heads: $headsCount"
                } else {
                    coinImageView.setImageResource(R.drawable.tails)
                    tailsCount++
                    tailsCountTextView.text = "Tails: $tailsCount"
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release media player resource
        mediaPlayer.release()
    }
}
