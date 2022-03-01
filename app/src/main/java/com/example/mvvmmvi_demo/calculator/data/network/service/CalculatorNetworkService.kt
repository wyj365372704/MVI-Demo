package com.example.mvvmmvi_demo.calculator.data.network.service

import com.example.mvvmmvi_demo.calculator.data.network.model.CalculateResultJson

internal interface CalculatorNetworkService {
    suspend fun calculateFromNet(input:String): CalculateResultJson
}