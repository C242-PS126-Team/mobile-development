package com.example.truecolors.ui.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.truecolors.data.test.TestQuestion

class ColorBlindTestViewModel : ViewModel() {
    private val _questionsList = MutableLiveData<List<TestQuestion>>()
    val questionsList: LiveData<List<TestQuestion>> = _questionsList

    private val _currentPosition = MutableLiveData(1)
    val currentPosition: LiveData<Int> = _currentPosition

    private val _selectedOptionPosition = MutableLiveData<Int>()
    val selectedOptionPosition: LiveData<Int> = _selectedOptionPosition

    private val _correctAnswers = MutableLiveData(0)
    val correctAnswers: LiveData<Int> = _correctAnswers

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    fun setQuestions(questions: List<TestQuestion>) {
        _questionsList.value = questions
    }

    fun setUserName(name: String) {
        _userName.value = name
    }

    fun setSelectedOption(option: Int) {
        _selectedOptionPosition.value = option
    }

    fun incrementCorrectAnswers() {
        _correctAnswers.value = (_correctAnswers.value ?: 0) + 1
    }

    fun nextQuestion() {
        _currentPosition.value = (_currentPosition.value ?: 1) + 1
        _selectedOptionPosition.value = 0 // Reset opsi
    }
}
