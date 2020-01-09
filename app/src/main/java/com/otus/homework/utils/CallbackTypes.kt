package com.otus.homework.utils

import com.otus.homework.model.task.ChessTask
import com.otus.homework.model.user.UserShortData

typealias BasicCallback = () -> Unit
typealias RequestErrorCallback = (String) -> Unit
typealias RegisterUserCallback = (UserShortData) -> Unit
typealias ChessTaskCallback = (ChessTask) -> Unit