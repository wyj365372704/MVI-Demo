package com.example.mvvmmvi_demo.calculator.data

import com.example.mvvmmvi_demo.calculator.data.network.model.toDomainModel
import com.example.mvvmmvi_demo.calculator.data.network.service.CalculatorNetworkService
import com.example.mvvmmvi_demo.calculator.domain.model.CalculateResult
import com.example.mvvmmvi_demo.calculator.domain.repository.CalculateRepository
import java.io.IOException
import java.lang.Exception
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Repository is exposing data to the domain layer.
 * Depending on application structure and quality of the external APIs repository can also merge,
 * filter, and transform the data. The intention of these operations is to create
 * high-quality data source for the domain layer, not to perform any business
 * logic (domain layer use case responsibility).
 */
internal class CalculatorRepositoryImpl(private val calculatorNetworkService: CalculatorNetworkService) :
    CalculateRepository {
    override suspend fun calculate(input: String): CalculateResult {
        //DB option ？？if you want
        return calculatorNetworkService.calculateFromNet(input).toDomainModel()
    }

}