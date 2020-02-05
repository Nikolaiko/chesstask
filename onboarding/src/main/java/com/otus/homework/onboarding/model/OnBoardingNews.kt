package com.otus.homework.onboarding.model

import com.otus.homework.onboarding.model.enums.NewsMessageId

data class OnBoardingNews (
    val id:NewsMessageId,
    val message:String = ""
)