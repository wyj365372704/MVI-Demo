package com.example.mvvmmvi_demo

import android.app.Application
import com.example.mvvmmvi_demo.calculator.kodeinModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class MyApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))
        import(kodeinModule)
    }

}