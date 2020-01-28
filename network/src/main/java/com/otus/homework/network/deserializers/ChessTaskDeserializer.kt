package com.otus.homework.network.deserializers

import com.example.core_api.model.enums.ChessFigureColor
import com.example.core_api.model.task.ChessFigure
import com.example.core_api.model.task.ChessTask
import com.example.core_api.model.task.FigurePosition
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.otus.homework.network.getColorFromFigureChar
import com.otus.homework.network.getFigureTypeFromChar
import java.lang.reflect.Type

class ChessTaskDeserializer : JsonDeserializer<ChessTask> {
    companion object {
        private const val ID_FIELD_NAME:String = "id"
        private const val FEN_FIELD_NAME:String = "fen"
        private const val PGN_FIELD_NAME:String = "pgn"

        private const val DEFAULT_ID:String = "not_set"
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ChessTask {
        val jsonObject = json?.asJsonObject

        return ChessTask(jsonObject?.get(ID_FIELD_NAME)?.asString ?: DEFAULT_ID,
            getStartingPositions(jsonObject?.get(FEN_FIELD_NAME)?.asString),
            getStartingColor(jsonObject?.get(FEN_FIELD_NAME)?.asString))
    }

    private fun getStartingColor(fenString:String?): ChessFigureColor {
        if (fenString?.isNotBlank() == true) {
            val secondPart = fenString.split(' ')[1]
            return ChessFigureColor.valueOf(secondPart[0].toString())
        } else {
            return ChessFigureColor.w
        }
    }

    private fun getStartingPositions(fenString:String?):List<ChessFigure> {
        val startingPositions = mutableListOf<ChessFigure>()
        if (fenString?.isNotBlank() == true) {
            val firstPart = fenString.split(' ')[0]
            val rows = firstPart.split('/')

            var rowIndex = 0
            for (currentRow in rows) {
                var currentColumn:Int = 0
                for (currentChar in currentRow) {
                    if (currentChar.isDigit()) {
                        val skipCount = currentChar.toString().toInt()
                        currentColumn += skipCount
                    } else {
                        val figureColor = getColorFromFigureChar(currentChar)
                        val lowerCased = currentChar.toLowerCase()
                        val figureType = getFigureTypeFromChar(lowerCased)
                        val figurePosition = FigurePosition(rowIndex, currentColumn)

                        startingPositions.add(ChessFigure(figureType, figureColor, figurePosition))
                        currentColumn += 1
                    }
                }
                rowIndex += 1
            }
        }
        return startingPositions
    }
}