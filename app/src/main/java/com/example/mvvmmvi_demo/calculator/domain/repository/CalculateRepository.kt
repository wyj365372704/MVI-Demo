package com.example.mvvmmvi_demo.calculator.domain.repository

import com.example.mvvmmvi_demo.calculator.domain.model.CalculateResult

/**
 * Repository interface:
 *  required to keep the domain layer independent from the data layer
 */
internal interface CalculateRepository {
    suspend fun calculate(input: String): CalculateResult
}