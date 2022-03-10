package com.cornershop.presentation.ui.createCounter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cornershop.presentation.R
import com.cornershop.presentation.databinding.CreateCounterFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateCounterFragment : Fragment() {

    private lateinit var binding: CreateCounterFragmentBinding

    private val createCounterViewModel: CreateCounterViewModel by viewModel()

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
                createCounterViewModel.saveCounter(name.toString())
                binding.txtInputCounterName.helperText = null
            } else {
                binding.txtInputCounterName.helperText =
                    context?.let {
                    resources.getString(R.string.create_counter_disclaimer)
                } + "\n" + context?.let { resources.getStringArray(R.array.drinks_array) }
                    ?.map { it }
            }
        }
    }
}
