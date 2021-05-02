package com.bignerdranch.android.geoquiz

import androidx.annotation.StringRes

data class Question(
    @StringRes val textResId: Int,
    val answer: Boolean,
    var hasBeenAnswered: Boolean = false,
    var score: Int = 0,
    var isCheater: Boolean = false
)