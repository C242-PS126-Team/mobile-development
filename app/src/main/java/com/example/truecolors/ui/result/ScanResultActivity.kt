package com.example.truecolors.ui.result

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.truecolors.R
import com.example.truecolors.data.remote.response.Predictions
import com.example.truecolors.databinding.ActivityScanResultBinding
import com.example.truecolors.ui.MainActivity
import com.google.gson.Gson

@Suppress("DEPRECATION")
class ScanResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScanResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.hide()

        binding.customAppBar.appBarTitle.text = getString(R.string.color_scan_result)
        binding.customAppBar.appBarSubtitle.visibility = View.GONE

        val predictionsJson = intent.getStringExtra("predictions")
        predictionsJson?.let {
            val predictions = Gson().fromJson(it, Predictions::class.java)
            displayPredictions(predictions)
        } ?: Log.e("ScanResultActivity", "No predictions data found")
    }

    @SuppressLint("DefaultLocale")
    private fun displayPredictions(predictions: Predictions) {
        binding.tvNameResult.text = predictions.predictedClass
        binding.tvRgbResult.text = predictions.rgb

        val confidencePercentage = (predictions.confidence ?: 0.0) * 100
        binding.tvAccuracyResult.text = String.format("%.2f%%", confidencePercentage)
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}