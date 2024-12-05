package com.example.truecolors.ui.result

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.truecolors.data.test.TestConstant
import com.example.truecolors.databinding.ActivityTestResultBinding
import com.example.truecolors.ui.MainActivity

@Suppress("DEPRECATION")
class TestResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestResultBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val username = intent.getStringExtra(TestConstant.USER_NAME)
        binding.name.text = "Hello $username"

        val totalQuestions = intent.getIntExtra(TestConstant.TOTAL_QUESTIONS, 0)
        val correctAnswers = intent.getIntExtra(TestConstant.CORRECT_ANSWERS, 0)

        binding.score.text = "$correctAnswers / $totalQuestions"

        val evaluator = TestResultEvaluator()
        val result = evaluator.evaluate(correctAnswers)

        binding.quizResult.text = result.result
        binding.detailResult.text = result.detail
        binding.recommendationResult.text = result.recommendation
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}