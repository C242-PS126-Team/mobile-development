package com.example.truecolors.ui.result

import com.example.truecolors.data.test.TestResult

class TestResultEvaluator {
    fun evaluate(correctAnswers: Int):
            TestResult = when {
                correctAnswers >= 12 -> TestResult(
                    result = "Normal Vision",
                    detail = "No issue with your vision about color",
                    recommendation = "No action needed. Your vision is normal"
                )
                correctAnswers in 9..11 -> TestResult(
                    result = "Low Partially Color Blind",
                    detail = "Low Red-Green Deficiency: You have low distinguishing ability in green and red",
                    recommendation = "Because there are low deficiency, you need to check with specialist"
                )
                else -> TestResult(
                    result = "You are Partially Color Blind",
                    detail = "Red-Green Deficiency: You have difficulty distinguishing ability in green and red colors",
                    recommendation = "We recommend consulting an eye specialist for a more detailed analysis."
                )
            }
}