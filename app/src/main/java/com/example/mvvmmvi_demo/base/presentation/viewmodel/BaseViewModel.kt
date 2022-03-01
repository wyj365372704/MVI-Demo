package com.example.mvvmmvi_demo.base.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.properties.Delegates

/**
 * ViewModel:
 * dispatches (through LiveData) state changes to the view and deals with user interactions
 *
 * @param initialState 初始状态
 */
abstract class BaseViewModel<ViewState : BaseViewState, ViewIntent : BaseIntent>(initialState: ViewState) :
    ViewModel() {
    private val stateMutableLiveData = MutableLiveData<ViewState>()

    /**
     * ViewState ,提供给UI订阅。
     */
    val stateLiveData: LiveData<ViewState> = stateMutableLiveData

    private var _state by Delegates.observable(initialState) { _, _, new ->
        stateMutableLiveData.postValue(new)
    }

    protected fun sendState(viewState: ViewState) {
        _state = viewState
    }

    /**
     * 处理来自UI层的Intent，ViewModel需要调用的Domain层完成业务逻辑后，再根据Domain的返回，
     * 通知UI层响应ViewState更新UI显示。
     *
     * @see sendState
     */
    abstract fun handleIntent(viewIntent: ViewIntent)
}