package com.example.truecolors.ui.test

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.truecolors.R
import com.example.truecolors.data.test.TestConstant
import com.example.truecolors.data.test.TestQuestion
import com.example.truecolors.databinding.ActivityTestQuestionBinding
import com.example.truecolors.ui.result.TestResultActivity

class TestQuestionActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityTestQuestionBinding

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<TestQuestion>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // Check if mUserName is null
        try {
            mUserName = intent.getStringExtra(TestConstant.USER_NAME)
            if (mUserName.isNullOrEmpty()) {
                throw IllegalArgumentException("User name is missing")
            }
        } catch (e: Exception) {
            Log.e("TestQuestionActivity", "Error getting username: ${e.message}")
            Toast.makeText(this, "User name is missing", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Check if questions are null or empty
        try {
            mQuestionsList = TestConstant.getQuestions()
            if (mQuestionsList.isNullOrEmpty()) {
                throw IllegalStateException("Questions list is empty or null")
            }
        } catch (e: Exception) {
            Log.e("TestQuestionActivity", "Error loading questions: ${e.message}")
            Toast.makeText(this, "No questions available", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setQuestion()

        binding.tvOption1.setOnClickListener(this)
        binding.tvOption2.setOnClickListener(this)
        binding.tvOption3.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        try {
            val question = mQuestionsList!![mCurrentPosition - 1]

            defaultOptionsView()

            if (mCurrentPosition == mQuestionsList!!.size) {
                binding.btnSubmit.text = getText(R.string.finish)
            } else {
                binding.btnSubmit.text = getText(R.string.submit)
            }

            binding.pb.progress = mCurrentPosition
            binding.pb.max = mQuestionsList!!.size
            binding.tvPb.text = "$mCurrentPosition" + "/" + binding.pb.max

            binding.tvOption1.text = question.optionOne
            binding.tvOption2.text = question.optionTwo
            binding.tvOption3.text = question.optionThree
            binding.ivQuestion.setImageResource(question.plate)
        } catch (e: Exception) {
            Log.e("TestQuestionActivity", "Error setting question: ${e.message}")
        }
    }

    private fun defaultOptionsView() {
        try {
            val options = ArrayList<TextView>()
            options.add(0, binding.tvOption1)
            options.add(1, binding.tvOption2)
            options.add(2, binding.tvOption3)

            for (option in options) {
                option.setTextColor(ContextCompat.getColor(this, R.color.blue_500))
                option.typeface = ResourcesCompat.getFont(this, R.font.poppins)
                option.background = ContextCompat.getDrawable(this, R.drawable.rectangle1)
            }
        } catch (e: Exception) {
            Log.e("TestQuestionActivity", "Error resetting options: ${e.message}")
        }
    }

    override fun onClick(v: View?) {
        try {
            when (v?.id) {
                R.id.tv_option1 -> {
                    selectedOptionView(binding.tvOption1, 1)
                }
                R.id.tv_option2 -> {
                    selectedOptionView(binding.tvOption2, 2)
                }
                R.id.tv_option3 -> {
                    selectedOptionView(binding.tvOption3, 3)
                }
                R.id.btn_submit -> {
                    if (mSelectedOptionPosition == 0) {
                        Toast.makeText(this, getText(R.string.warning_option), Toast.LENGTH_SHORT).show()
                    } else {
                        if (mSelectedOptionPosition == mQuestionsList!![mCurrentPosition - 1].correctAnswer) {
                            mCorrectAnswers++
                            Log.d("TestQuestionActivity", "Correct Answers incremented to: $mCorrectAnswers")
                        }

                        if (mCurrentPosition == mQuestionsList!!.size) {
                            binding.btnSubmit.text = getText(R.string.finish)

                            val intent = Intent(this, TestResultActivity::class.java)
                            intent.putExtra(TestConstant.USER_NAME, mUserName)
                            intent.putExtra(TestConstant.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(TestConstant.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                        } else {
                            binding.btnSubmit.text = getText(R.string.next)
                        }

                        mSelectedOptionPosition = 0

                        if (mCurrentPosition < mQuestionsList!!.size) {
                            mCurrentPosition++
                            setQuestion()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TestQuestionActivity", "Error in onClick: ${e.message}")
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOption: Int) {
        try {
            defaultOptionsView()
            mSelectedOptionPosition = selectedOption

            tv.setTextColor(ContextCompat.getColor(this, R.color.blue_500))
            tv.typeface = ResourcesCompat.getFont(this, R.font.poppins)
            tv.background = ContextCompat.getDrawable(this, R.drawable.rectangle_rounded_blue_100)
        } catch (e: Exception) {
            Log.e("TestQuestionActivity", "Error in selectedOptionView: ${e.message}")
        }
    }
}
