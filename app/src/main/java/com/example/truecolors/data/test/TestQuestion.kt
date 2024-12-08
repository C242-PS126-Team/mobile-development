package com.example.truecolors.data.test

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TestQuestion(
    val id: Int,
    val plate: Int,
    val optionOne: String,
    val optionTwo: String,
    val optionThree: String,
    val correctAnswer: Int
) : Parcelable
