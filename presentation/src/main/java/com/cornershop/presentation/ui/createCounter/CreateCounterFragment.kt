package com.cornershop.presentation.ui.createCounter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cornershop.domain.commons.StringUtils.COMMA_SEPARATOR
import com.cornershop.domain.commons.StringUtils.EMPTY_STRING
import com.cornershop.domain.commons.StringUtils.NEW_LINE
import com.cornershop.presentation.R
import com.cornershop.presentation.databinding.CreateCounterFragmentBinding
import com.cornershop.presentation.ui.counterList.CounterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateCounterFragment : Fragment() {

    private lateinit var binding: CreateCounterFragmentBinding

    private val counterViewModel: CounterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CreateCounterFragmentBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        setUpViews()
    }

    private fun setUpObservers() {
        // method
    }

    private fun setUpViews() {
        binding.btnClose.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.txtBtnSave.setOnClickListener {
            val name = binding.edTxtCounterName.text
            if (!name.isNullOrEmpty()) {
                counterViewModel.saveCounter(name.toString())
                binding.txtInputCounterName.helperText = null
            } else {
                binding.txtInputCounterName.helperText = helperText()
            }
        }
    }

    private fun helperText(): String {
        val helperMsg =
            context?.let { resources.getString(R.string.create_counter_disclaimer) } ?: EMPTY_STRING
        val helperOptions =
            context?.let {
                resources.getStringArray(R.array.drinks_array).joinToString(COMMA_SEPARATOR)
            } ?: EMPTY_STRING

        return helperMsg + NEW_LINE + helperOptions
    }
}
