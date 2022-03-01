package com.example.mvvmmvi_demo.base.presentation.fragment

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

abstract class InjectionFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId),
    KodeinAware {
    final override val kodein: Kodein by kodein()
}