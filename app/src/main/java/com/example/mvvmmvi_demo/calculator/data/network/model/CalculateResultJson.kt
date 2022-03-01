package com.example.mvvmmvi_demo.calculator.data.network.model

import com.example.mvvmmvi_demo.calculator.domain.model.CalculateResult
import com.squareup.moshi.Json

internal data class CalculateResultJson(
    @field:Json(name = "EXPRESSION") val expression: String,
    @field:Json(name = "RESULT") val result: String
)

internal fun CalculateResultJson.toDomainModel(): CalculateResult {
    return CalculateResult(this.expression, this.result.toDouble())
}