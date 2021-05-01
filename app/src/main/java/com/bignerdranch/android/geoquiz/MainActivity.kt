package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
            Question(R.string.question_australia, true),
            Question(R.string.question_oceans, true),
            Question(R.string.question_mideast, false),
            Question(R.string.question_africa, false),
            Question(R.string.question_americas, true),
            Question(R.string.question_asia, true))

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.btnNext)
        previousButton = findViewById(R.id.btnPrevious)
        questionTextView = findViewById(R.id.txtQuestion)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            updateAnswerButtonState()
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            updateAnswerButtonState()
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        previousButton.setOnClickListener {
            currentIndex = if (currentIndex == 0) questionBank.size - 1
            else (currentIndex - 1) % questionBank.size
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        updateQuestion()
    }

    private fun updateQuestion() {
        questionTextView.setText(questionBank[currentIndex].textResId)
        updateAnswerButtonState()
    }

    private fun getScoreRating() {
        if (currentIndex == questionBank.size - 1) {
            val totalScore = questionBank.sumBy { it.score }
            Toast.makeText(this, "总得分：${totalScore * 100 / questionBank.size}%", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        questionBank[currentIndex].hasBeenAnswered = true
        val isAnswerCorrect = userAnswer == correctAnswer

        val messageResId = if (isAnswerCorrect) R.string.correct_toast else R.string.incorrect_toast
        questionBank[currentIndex].score = if (isAnswerCorrect) 1 else 0

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        getScoreRating()
    }

    /**
     * 3.8挑战练习：用户答完某道题，就林掉那道题对应的按钮，用户一题多答
     */
    private fun updateAnswerButtonState() {
        if (questionBank[currentIndex].hasBeenAnswered) {
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        } else {
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
    }
}