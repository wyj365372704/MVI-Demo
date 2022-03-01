package com.example.mvvmmvi_demo.calculator

import com.example.mvvmmvi_demo.calculator.data.dataModule
import com.example.mvvmmvi_demo.calculator.domain.domainModule
import com.example.mvvmmvi_demo.calculator.presentation.presentationModule
import org.kodein.di.Kodein

internal val kodeinModule = Kodein.Module("Calculator") {
    import(presentationModule)
    import(domainModule)
    import(dataModule)
}