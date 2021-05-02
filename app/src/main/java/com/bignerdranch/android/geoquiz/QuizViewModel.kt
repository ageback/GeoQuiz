package com.bignerdranch.android.geoquiz

import android.widget.Toast
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
private const val MAX_CHEAT_COUNT = 3

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

    val currentQuestionHasBeenAnswered: Boolean get() = questionBank[currentIndex].hasBeenAnswered

    val currentQuestionIsCheater: Boolean get() = questionBank[currentIndex].isCheater

    val isLastQuestion: Boolean get() = currentIndex == questionBank.size - 1


    fun moveToPrevious() {
        currentIndex = if (currentIndex == 0) questionBank.size - 1
        else (currentIndex - 1) % questionBank.size
    }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun setCurrentIsCheater(isCheater: Boolean) {
        questionBank[currentIndex].isCheater = isCheater
    }

    fun setCurrentHasBeenAnswered(hasBeenAnswered: Boolean) {
        questionBank[currentIndex].hasBeenAnswered = hasBeenAnswered
    }

    fun setCurrentScore(score: Int) {
        questionBank[currentIndex].score = score
    }

    fun getScoreRating(): Int {
        val totalScore = questionBank.sumBy { it.score }
        return totalScore * 100 / questionBank.size
    }

    /**
     * 检查作弊次数是否达到3次。大于等于3次就返回false.
     */
    fun checkCheatCount(): Boolean {
        return questionBank[currentIndex].cheatCount >= 3
    }

    fun increaseCheatCount(){
        questionBank[currentIndex].cheatCount++
    }
}