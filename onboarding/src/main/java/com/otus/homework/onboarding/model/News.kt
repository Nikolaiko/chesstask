package com.otus.homework.onboarding.model

import com.otus.homework.onboarding.model.enums.NewsMessageId

data class News (val id:NewsMessageId,
                 val message:String = "")