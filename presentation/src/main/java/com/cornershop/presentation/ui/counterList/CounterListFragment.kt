package com.cornershop.presentation.ui.counterList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cornershop.domain.commons.StringUtils.COMMA_SEPARATOR
import com.cornershop.domain.models.Counter
import com.cornershop.presentation.R
import com.cornershop.presentation.databinding.CounterListFragmentBinding
import com.cornershop.presentation.ui.baseViews.BaseFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CounterListFragment : BaseFragment() {

    private lateinit var binding: CounterListFragmentBinding

    private val counterViewModel: CounterViewModel by viewModel()
    private val counterAdapter: CounterAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CounterListFragmentBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewObservers()
        setUpViews()
        setUpCountersRecyclerView()
        loadData()
    }

    private fun setUpViewObservers() {
        with(counterViewModel) {

            serviceStatusHandlerLiveData.observe(viewLifecycleOwner) { serviceHandler ->
                serviceStatusFlow.value = serviceHandler
            }

            counterListLiveData.observe(viewLifecycleOwner) { counters ->
                binding.refreshCounters.isRefreshing = false
                counterAdapter.replaceItems(counters)
            }

            showLoadingLiveDada.observe(viewLifecycleOwner) { condition ->
                if (condition) {
                    binding.refreshCounters.isRefreshing = false
                    binding.rsvCounterList.showLoadingView()
                }
            }

            showEmptyViewLiveData.observe(viewLifecycleOwner) { condition ->
                if (condition) {
                    binding.refreshCounters.isRefreshing = false
                    binding.rsvCounterList.showEmptyView()
                }
            }

            showListViewLiveData.observe(viewLifecycleOwner) { condition ->
                if (condition) {
                    binding.refreshCounters.isRefreshing = false
                    counterListLiveData.value?.let { counterList ->
                        binding.rsvCounterList.showRecyclerAndTotalsViews(counterList)
                    }
                }
            }

            showErrorViewLiveData.observe(viewLifecycleOwner) { condition ->
                if (condition) {
                    binding.refreshCounters.isRefreshing = false
                    binding.rsvCounterList.setOnRetryClickListener {
                        counterViewModel.fetchCounterList()
                    }
                    binding.rsvCounterList.showErrorView()
                }
            }
        }
    }

    private fun setUpViews() {
        binding.fabAddCounter.setOnClickListener {
            findNavController().navigate(
                CounterListFragmentDirections.actionHomeListToCreateCounter()
            )
        }
        binding.refreshCounters.setOnRefreshListener {
            binding.cardSearch.visibility = View.VISIBLE
            binding.customToolbar.root.visibility = View.GONE
            counterViewModel.fetchCounterList()
        }
        binding.customToolbar.imgCloseToolbar.setOnClickListener {
            binding.cardSearch.visibility = View.VISIBLE
            binding.customToolbar.root.visibility = View.GONE

            loadData()
        }
        binding.customToolbar.imgDeleteToolbar.setOnClickListener {
            val counterList = counterAdapter.getItemsList()
            if (counterList.any { it.isSelected }) {
                val countersSelectedList = counterList.filter { counter -> counter.isSelected }
                context?.let {
                    MaterialAlertDialogBuilder(it)
                        .setMessage(
                            it.getString(
                                R.string.delete_x_question,
                                countersSelectedList.joinToString(COMMA_SEPARATOR) { counter -> counter.title }
                            )
                        )
                        .setPositiveButton(it.getString(R.string.delete)) { dialog, _ ->
                            counterViewModel.deleteCounterList(countersSelectedList)
                            binding.cardSearch.visibility = View.VISIBLE
                            binding.customToolbar.root.visibility = View.GONE
                            dialog.dismiss()
                        }
                        .setNegativeButton(it.getString(R.string.cancel)) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        }
    }

    private fun setUpCountersRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this.context)
        binding.rsvCounterList.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = counterAdapter
        }

        counterAdapter.addListener(object : CounterAdapter.Listener {
            override fun itemLongClicked(counters: List<Counter>) {
                binding.cardSearch.visibility = View.INVISIBLE
                binding.customToolbar.root.visibility = View.VISIBLE

                binding.customToolbar.txtToolbarDeleteTitle.text = context?.getString(
                    R.string.n_selected,
                    1
                )
            }

            override fun itemSelectionClicked(counters: List<Counter>) {
                binding.customToolbar.txtToolbarDeleteTitle.text = context?.getString(
                    R.string.n_selected,
                    counters.filter { it.isSelected }.size
                )
                if (counters.none { it.isSelected }) {
                    binding.cardSearch.visibility = View.VISIBLE
                    binding.customToolbar.root.visibility = View.GONE

                    loadData()
                }
            }
        })
    }

    private fun loadData() {
        counterViewModel.fetchCounterList()
    }
}
