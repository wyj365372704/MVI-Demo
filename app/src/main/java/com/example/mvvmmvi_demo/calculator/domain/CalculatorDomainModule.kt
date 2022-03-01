package com.example.mvvmmvi_demo.calculator.domain

import com.example.mvvmmvi_demo.calculator.domain.usecase.CalculateUseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal val domainModule = Kodein.Module("domain"){
    bind() from singleton { CalculateUseCase(instance()) }
}