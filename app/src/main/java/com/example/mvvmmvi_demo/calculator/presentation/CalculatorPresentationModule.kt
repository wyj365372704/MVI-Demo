package com.example.mvvmmvi_demo.calculator.presentation

import androidx.fragment.app.Fragment
import com.example.mvvmmvi_demo.base.di.KotlinViewModelProvider
import com.example.mvvmmvi_demo.calculator.presentation.calculatormain.CalculatorMainViewModel
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

internal val presentationModule = Kodein.Module("presentation"){
    bind<CalculatorMainViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context) {CalculatorMainViewModel(instance())}
    }
}