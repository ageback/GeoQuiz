package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var cheatButton: Button

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        quizViewModel.currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.btnNext)
        previousButton = findViewById(R.id.btnPrevious)
        questionTextView = findViewById(R.id.txtQuestion)
        cheatButton = findViewById(R.id.cheat_button)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
//            updateAnswerButtonState()
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
//            updateAnswerButtonState()
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        previousButton.setOnClickListener {
//            currentIndex = if (currentIndex == 0) questionBank.size - 1
//            else (currentIndex - 1) % questionBank.size
            updateQuestion()
        }

        cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)

        }

        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        updateQuestion()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.setCurrentIsCheater(data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false)
        }
    }

    private fun updateQuestion() {
        questionTextView.setText(quizViewModel.currentQuestionText)
//        updateAnswerButtonState()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
//        questionBank[currentIndex].hasBeenAnswered = true
        val isAnswerCorrect = userAnswer == correctAnswer

        val messageResId = when {
            quizViewModel.currentQuestionIsCheater -> R.string.judgment_toast
            isAnswerCorrect -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
//        questionBank[currentIndex].score = if (isAnswerCorrect) 1 else 0

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
//        if (currentIndex == questionBank.size - 1) getScoreRating()
    }

//    private fun getScoreRating() {
//        val totalScore = questionBank.sumBy { it.score }
//        Toast.makeText(this, "总得分：${totalScore * 100 / questionBank.size}%", Toast.LENGTH_SHORT)
//            .show()
//    }
//

//
//    /**
//     * 3.8挑战练习：用户答完某道题，就林掉那道题对应的按钮，用户一题多答
//     */
//    private fun updateAnswerButtonState() {
//        if (questionBank[currentIndex].hasBeenAnswered) {
//            trueButton.isEnabled = false
//            falseButton.isEnabled = false
//        } else {
//            trueButton.isEnabled = true
//            falseButton.isEnabled = true
//        }
//    }
}