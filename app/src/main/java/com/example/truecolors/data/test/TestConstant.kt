package com.example.truecolors.data.test

import com.example.truecolors.R

object TestConstant {
    const val USER_NAME: String = "username"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"

    fun getQuestions(): ArrayList<TestQuestion> {
        val questionList = ArrayList<TestQuestion>()

        val que1 = TestQuestion(
            1, R.drawable.plate1, "69", "96", "87", 1
        )

        questionList.add(que1)

        val que2 = TestQuestion(
            2, R.drawable.plate2, "64", "30", "54", 3
        )

        questionList.add(que2)

        val que3 = TestQuestion(
            3, R.drawable.plate3, "25", "15", "75", 2
        )

        questionList.add(que3)

        val que4 = TestQuestion(
            4, R.drawable.plate4, "7", "2", "5", 2
        )

        questionList.add(que4)

        val que5 = TestQuestion(
            5, R.drawable.plate5, "25", "15", "35", 1
        )

        questionList.add(que5)

        val que6 = TestQuestion(
            6, R.drawable.plate6, "6", "3", "8", 3
        )

        questionList.add(que6)

        val que7 = TestQuestion(
            7, R.drawable.plate7, "22", "12", "72", 3
        )

        questionList.add(que7)

        val que8 = TestQuestion(
            8, R.drawable.plate8, "6", "3", "8", 2
        )

        questionList.add(que8)

        val que9 = TestQuestion(
            9, R.drawable.plate9, "38", "83", "88", 1
        )

        questionList.add(que9)

        val que10 = TestQuestion(
            10, R.drawable.plate10, "83", "63", "98", 2
        )

        questionList.add(que10)

        val que11 = TestQuestion(
            11, R.drawable.plate11, "65", "36", "85", 3
        )

        questionList.add(que11)

        val que12 = TestQuestion(
            12, R.drawable.plate12, "78", "23", "16", 1
        )

        questionList.add(que12)

        val que13 = TestQuestion(
            13, R.drawable.plate13, "83", "87", "32", 3
        )

        questionList.add(que13)

        val que14 = TestQuestion(
            14, R.drawable.plate14, "8", "9", "6", 2
        )

        questionList.add(que14)

        return questionList
    }
}