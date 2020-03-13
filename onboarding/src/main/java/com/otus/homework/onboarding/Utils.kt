package com.otus.homework.onboarding

import com.example.core.model.user.UserProfile

const val MIN_EMAIL_LENGTH: Int = 3
const val DEBOUNCE_VALUE: Long = 500

fun isUserDataCorrect(data: UserProfile): Boolean {
    return data.username.length >= MIN_EMAIL_LENGTH && data.password.isNotEmpty()
}
