package com.example.mvvmmvi_demo.calculator.presentation.calculatormain

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import com.example.mvvmmvi_demo.R
import com.example.mvvmmvi_demo.base.presentation.fragment.InjectionFragment
import com.example.mvvmmvi_demo.base.delegate.viewBinding
import com.example.mvvmmvi_demo.databinding.FragmentCalculatorMainBinding
import org.kodein.di.generic.instance

/**
 * View (Fragment) - presents data on the screen and pass user interactions to View Model. Views are hard to test, so they should be as simple as possible.
 */
class CalculatorMainFragment : InjectionFragment(R.layout.fragment_calculator_main) {

    private val binding: FragmentCalculatorMainBinding by viewBinding()

    private val viewModel: CalculatorMainViewModel by instance()

    lateinit var progressDialog: ProgressDialog

    /**
     * view层响应state变化，依据state类型做出UI更新
     */
    private val stateObserver = Observer<CalculatorMainViewModel.State> {
        when (it) {
            is CalculatorMainViewModel.State.Error -> {
                binding.tvShow.text = "ERROR"
                progressDialog.dismiss()
            }
            is CalculatorMainViewModel.State.Success -> {
                binding.tvShow.text = it.result
                progressDialog.dismiss()
            }
            is CalculatorMainViewModel.State.Input -> {
                if (TextUtils.isEmpty(it.input)) {
                    binding.tvShow.text = "0"
                } else {
                    binding.tvShow.text = it.input
                }
                progressDialog.dismiss()
            }
            is CalculatorMainViewModel.State.Requesting -> {
                progressDialog.show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = ProgressDialog(requireContext()).also {
            it.setTitle("计算中...")
            it.setMessage("这是一个云计算过程，不明觉厉是不？")
        }
        //view订阅state ，界面的一切变化由它驱动，state可以包含要更新显示的数据。
        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)

        handleClickEvent()
    }

    //view层调用到ViewModel层，处理业务逻辑
    private fun handleClickEvent() {
        val onClickListener = View.OnClickListener {
            when (it.id) {
                R.id.bt_0 -> {
                    viewModel.handleIntent(CalculatorMainViewModel.Intent.InputIntent("0"))
                }
                R.id.bt_1 -> {
                    viewModel.handleIntent(CalculatorMainViewModel.Intent.InputIntent("1"))
                }
                R.id.bt_2 -> {
                    viewModel.handleIntent(CalculatorMainViewModel.Intent.InputIntent("2"))
                }
                R.id.bt_3 -> {
                    viewModel.handleIntent(CalculatorMainViewModel.Intent.InputIntent("3"))
                }
                R.id.bt_4 -> {
                    viewModel.handleIntent(CalculatorMainViewModel.Intent.InputIntent("4"))
                }
                R.id.bt_5 -> {
                    viewModel.handleIntent(CalculatorMainViewModel.Intent.InputIntent("5"))
                }
                R.id.bt_6 -> {
                    viewModel.handleIntent(CalculatorMainViewModel.Intent.InputIntent("6"))
                }
                R.id.bt_7 -> {
                    viewModel.handleIntent(CalculatorMainViewModel.Intent.InputIntent("7"))
                }
                R.id.bt_8 -> {
                    viewModel.handleIntent(CalculatorMainViewModel.Intent.InputIntent("8"))
                }
                R.id.bt_9 -> {
                    viewModel.handleIntent(CalculatorMainViewModel.Intent.InputIntent("9"))
                }
                R.id.bt_dot -> {
                    viewModel.handleIntent(CalculatorMainViewModel.Intent.InputIntent("."))
                }
                R.id.bt_add -> {
                    viewModel.handleIntent(CalculatorMainViewModel.Intent.InputIntent("+"))
                }
                R.id.bt_subtract -> {
                    viewModel.handleIntent(CalculatorMainViewModel.Intent.InputIntent("-"))
                }
                R.id.bt_multiply -> {
                    viewModel.handleIntent(CalculatorMainViewModel.Intent.InputIntent("*"))
                }
                R.id.bt_divide -> {
                    viewModel.handleIntent(CalculatorMainViewModel.Intent.InputIntent("/"))
                }
                R.id.bt_back -> {
                    viewModel.handleIntent(CalculatorMainViewModel.Intent.BackIntent)
                }
                R.id.bt_equals -> {
                    viewModel.handleIntent(CalculatorMainViewModel.Intent.EqualIntent)
                }
                R.id.bt_clean -> {
                    viewModel.handleIntent(CalculatorMainViewModel.Intent.CleanIntent)
                }
            }
        }
        binding.bt0.setOnClickListener(onClickListener)
        binding.bt1.setOnClickListener(onClickListener)
        binding.bt2.setOnClickListener(onClickListener)
        binding.bt3.setOnClickListener(onClickListener)
        binding.bt4.setOnClickListener(onClickListener)
        binding.bt5.setOnClickListener(onClickListener)
        binding.bt6.setOnClickListener(onClickListener)
        binding.bt7.setOnClickListener(onClickListener)
        binding.bt8.setOnClickListener(onClickListener)
        binding.bt9.setOnClickListener(onClickListener)
        binding.btDot.setOnClickListener(onClickListener)
        binding.btAdd.setOnClickListener(onClickListener)
        binding.btSubtract.setOnClickListener(onClickListener)
        binding.btMultiply.setOnClickListener(onClickListener)
        binding.btDivide.setOnClickListener(onClickListener)
        binding.btBack.setOnClickListener(onClickListener)
        binding.btClean.setOnClickListener(onClickListener)
        binding.btEquals.setOnClickListener(onClickListener)
    }
}