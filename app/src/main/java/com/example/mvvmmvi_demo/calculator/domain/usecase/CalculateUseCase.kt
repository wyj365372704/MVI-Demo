package com.example.mvvmmvi_demo.calculator.domain.usecase

import com.example.mvvmmvi_demo.calculator.domain.model.CalculateResult
import com.example.mvvmmvi_demo.calculator.domain.repository.CalculateRepository

/**
 * UseCase:
 *  contains business logic
 */
internal class CalculateUseCase(
    private val calculateRepository: CalculateRepository
) {

    suspend fun calculate(input: String): Result<CalculateResult> {
        return try {
            Result.success(calculateRepository.calculate(input))
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}