package com.otus.homework.chessclient.onboarding.model

import com.otus.homework.chessclient.onboarding.model.enums.NewsMessageId

data class News (val id:NewsMessageId,
                 val message:String = "")