package com.otus.homework.onboarding.model

data class RegistrationState(val backButtonEnabled:Boolean = true,
                             val registrationButtonEnabled:Boolean = true,
                             val loadingActive:Boolean = false,
                             val loginTextFieldEnabled:Boolean = true,
                             val passwordTextField:Boolean = true) {
}