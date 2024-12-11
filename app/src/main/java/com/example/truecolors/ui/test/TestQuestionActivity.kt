package com.example.truecolors.ui.test

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.truecolors.R
import com.example.truecolors.data.test.TestConstant
import com.example.truecolors.databinding.ActivityTestQuestionBinding
import com.example.truecolors.ui.result.TestResultActivity

class TestQuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestQuestionBinding
    private lateinit var viewModel: ColorBlindTestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ColorBlindTestViewModel::class.java]
        supportActionBar?.hide()

        if (viewModel.questionsList.value == null) {
            val userName = intent.getStringExtra(TestConstant.USER_NAME) ?: ""
            viewModel.setUserName(userName)

            val questions = TestConstant.getQuestions().shuffled()
            if (questions.isEmpty()) {
                Toast.makeText(this, "No questions available", Toast.LENGTH_SHORT).show()
                finish()
                return
            }
            viewModel.setQuestions(questions)
        }

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.currentPosition.observe(this) { position ->
            setQuestion(position)
        }
    }

    private fun setupListeners() {
        binding.tvOption1.setOnClickListener { selectOption(1) }
        binding.tvOption2.setOnClickListener { selectOption(2) }
        binding.tvOption3.setOnClickListener { selectOption(3) }
        binding.btnSubmit.setOnClickListener { handleSubmit() }
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion(position: Int) {
        val question = viewModel.questionsList.value?.getOrNull(position - 1) ?: return

        binding.tvOption1.text = question.optionOne
        binding.tvOption2.text = question.optionTwo
        binding.tvOption3.text = question.optionThree
        binding.ivQuestion.setImageResource(question.plate)

        binding.pb.progress = position
        binding.pb.max = viewModel.questionsList.value?.size ?: 0
        binding.tvPb.text = "$position/${binding.pb.max}"

        binding.btnSubmit.text = if (position == binding.pb.max) {
            getString(R.string.finish)
        } else {
            getString(R.string.submit)
        }

        defaultOptionsView()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun defaultOptionsView() {
        val options = listOf(binding.tvOption1, binding.tvOption2, binding.tvOption3)
        for (option in options) {
            option.setTextColor(getColor(R.color.blue_500))
            option.background = getDrawable(R.drawable.rectangle1)
        }
    }

    private fun selectOption(option: Int) {
        viewModel.setSelectedOption(option)
        defaultOptionsView()

        val selectedView = when (option) {
            1 -> binding.tvOption1
            2 -> binding.tvOption2
            3 -> binding.tvOption3
            else -> null
        }

        selectedView?.setBackgroundResource(R.drawable.rectangle_rounded_blue_100)
    }

    private fun handleSubmit() {
        val position = viewModel.currentPosition.value ?: 1
        val question = viewModel.questionsList.value?.getOrNull(position - 1)

        if (question != null) {
            val selectedOption = viewModel.selectedOptionPosition.value ?: 0
            if (selectedOption == 0) {
                Toast.makeText(this, getString(R.string.warning_option), Toast.LENGTH_SHORT).show()
                return
            }

            if (selectedOption == question.correctAnswer) {
                viewModel.incrementCorrectAnswers()
            }

            if (position == viewModel.questionsList.value?.size) {
                val intent = Intent(this, TestResultActivity::class.java)
                intent.putExtra(TestConstant.USER_NAME, viewModel.userName.value)
                intent.putExtra(TestConstant.CORRECT_ANSWERS, viewModel.correctAnswers.value)
                intent.putExtra(TestConstant.TOTAL_QUESTIONS, viewModel.questionsList.value?.size)
                startActivity(intent)
                finish()
            } else {
                viewModel.nextQuestion()
            }
        } else {
            Toast.makeText(this, getString(R.string.warning_option), Toast.LENGTH_SHORT).show()
        }

    }
}
