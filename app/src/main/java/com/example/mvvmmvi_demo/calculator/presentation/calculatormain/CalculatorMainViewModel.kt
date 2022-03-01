package com.example.mvvmmvi_demo.calculator.presentation.calculatormain

import androidx.lifecycle.*
import com.example.mvvmmvi_demo.base.presentation.viewmodel.BaseIntent
import com.example.mvvmmvi_demo.base.presentation.viewmodel.BaseViewModel
import com.example.mvvmmvi_demo.base.presentation.viewmodel.BaseViewState
import com.example.mvvmmvi_demo.calculator.domain.model.CalculateResult
import com.example.mvvmmvi_demo.calculator.domain.usecase.CalculateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

internal class CalculatorMainViewModel(private val calculateUseCase: CalculateUseCase) :
    BaseViewModel<CalculatorMainViewModel.State, CalculatorMainViewModel.Intent>(State.Input()) {

    override fun handleIntent(viewIntent: Intent) {
        viewModelScope.launch(context = Dispatchers.IO) {
            when (viewIntent) {
                is Intent.InputIntent -> {
                    val input = viewIntent.input
                    if (stateLiveData.value !is State.Input) {
                        sendState(State.Input(input))
                    } else {
                        val v = (stateLiveData.value as State.Input).input + input
                        sendState(State.Input(v))
                    }
                }
                is Intent.BackIntent -> {
                    if (stateLiveData.value is State.Input) {
                        val currentInput = (stateLiveData.value as State.Input).input
                        val length = currentInput.length
                        if (length > 0) {
                            sendState(State.Input(currentInput.substring(0, length - 1)))
                        }
                    } else {
                        sendState(State.Input())
                    }
                }
                is Intent.CleanIntent -> {
                    sendState(State.Input())
                }
                is Intent.EqualIntent -> {
                    if (stateLiveData.value is State.Input) {
                        val exp = (stateLiveData.value as State.Input).input
                        flow {
                            val result = calculateUseCase.calculate(exp)
                            this.emit(result)
                        }.onStart {
                            sendState(State.Requesting)
                        }.collectLatest {
                            dispatchEqualResult(it)
                        }
                    }
                }
            }
        }
    }

    private fun dispatchEqualResult(result: Result<CalculateResult>) {
        val r = result.getOrNull()
        if (r == null) {
            sendState(State.Error)
        } else {
            sendState(State.Success(r.output.toString()))
        }
    }

    internal sealed interface State : BaseViewState {
        object Requesting : State
        object Error : State
        data class Input(val input: String = "") : State
        data class Success(val result: String) : State
    }

    internal sealed interface Intent : BaseIntent {
        object BackIntent : Intent
        object CleanIntent : Intent
        object EqualIntent : Intent
        data class InputIntent(val input: String) : Intent
    }

}