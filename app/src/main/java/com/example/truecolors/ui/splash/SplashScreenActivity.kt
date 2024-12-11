package com.example.truecolors.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.truecolors.R
import com.example.truecolors.ui.MainActivity

@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash_screen)

        val splashImage = findViewById<ImageView>(R.id.splashImage)
        val splashText = findViewById<TextView>(R.id.splashText)

        val fadeInImage = AlphaAnimation(0f, 1f)
        fadeInImage.duration = 1500
        splashImage.startAnimation(fadeInImage)

        val fadeInText = AlphaAnimation(0f, 1f)
        fadeInText.duration = 1500  

        splashText.startAnimation(fadeInText)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}