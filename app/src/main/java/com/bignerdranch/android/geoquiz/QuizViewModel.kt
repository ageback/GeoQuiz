package com.bignerdranch.android.geoquiz

import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    var currentIndex = 0


    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    val currentQuestionAnswer: Boolean get() = questionBank[currentIndex].answer

    val currentQuestionText: Int get() = questionBank[currentIndex].textResId

    val currentQuestionIsCheater: Boolean get() = questionBank[currentIndex].isCheater

//    val currentAnswerTextResId: Int
//        get() = when {
//            currentQuestionAnswer -> R.string.true_button
//            else -> R.string.false_button
//        }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun setCurrentIsCheater(isCheater: Boolean) {
        questionBank[currentIndex].isCheater = isCheater
    }
}