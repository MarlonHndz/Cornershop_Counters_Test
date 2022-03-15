package com.cornershop.presentation.ui.counterList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cornershop.domain.commons.Utils.COMMA_SEPARATOR
import com.cornershop.domain.models.Counter
import com.cornershop.domain.repositories.serviceHandler.ServiceCalled
import com.cornershop.presentation.R
import com.cornershop.presentation.databinding.CounterListFragmentBinding
import com.cornershop.presentation.ui.baseViews.BaseFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class CounterListFragment : BaseFragment() {

    private lateinit var binding: CounterListFragmentBinding

    private val counterListViewModel: CounterListViewModel by viewModel()
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
        with(counterListViewModel) {

            serviceStatusHandlerLiveData.observe(viewLifecycleOwner) { serviceHandler ->
                serviceStatusFlow.value = serviceHandler
            }

            counterListLiveData.observe(viewLifecycleOwner) { counters ->
                binding.refreshCounters.isRefreshing = false
                showList(counters)

                binding.searchBarView.visibility = View.VISIBLE
                binding.customDeleteToolbar.root.visibility = View.GONE
            }

            showLoadingLiveDada.observe(viewLifecycleOwner) { condition ->
                if (condition) {
                    binding.refreshCounters.isRefreshing = false
                    binding.rsvCounterList.showLoadingView()
                    binding.searchBarView.disableSearchBar()
                }
            }

            showDeleteLoadingLiveDada.observe(viewLifecycleOwner) { condition ->
                if (condition) {
                    binding.refreshCounters.isRefreshing = false
                    binding.customDeleteToolbar.pbDeleteLoading.visibility = View.VISIBLE
                    binding.customDeleteToolbar.imgDeleteToolbar.visibility = View.GONE
                } else {
                    binding.customDeleteToolbar.pbDeleteLoading.visibility = View.GONE
                    binding.customDeleteToolbar.imgDeleteToolbar.visibility = View.VISIBLE
                }
            }

            showEmptyViewLiveData.observe(viewLifecycleOwner) { condition ->
                if (condition) {
                    binding.refreshCounters.isRefreshing = false
                    binding.rsvCounterList.showEmptyView()
                    binding.searchBarView.disableSearchBar()
                }
            }

            showListViewLiveData.observe(viewLifecycleOwner) { condition ->
                if (condition) {
                    binding.refreshCounters.isRefreshing = false
                    counterListLiveData.value?.let { counterList ->
                        showList(counterList)
                    }
                    binding.searchBarView.activateSearchBar()
                }
            }

            showErrorViewLiveData.observe(viewLifecycleOwner) { condition ->
                if (condition) {
                    binding.refreshCounters.isRefreshing = false
                    binding.rsvCounterList.setOnRetryClickListener {
                        loadData()
                    }
                    binding.rsvCounterList.showErrorView()
                    binding.searchBarView.disableSearchBar()
                }
            }

            showErrorWithListViewLiveData.observe(viewLifecycleOwner) { condition ->
                if (condition) {
                    binding.refreshCounters.isRefreshing = false
                    counterListLiveData.value?.let { counterList ->
                        showList(counterList)
                    }
                    binding.txtConnectionError.visibility = View.VISIBLE
                    binding.searchBarView.activateSearchBar()
                } else {
                    binding.txtConnectionError.visibility = View.GONE
                }
            }

            showIncOrDecErrorViewLiveData.observe(viewLifecycleOwner) { condition ->
                if (condition) {
                    val serviceHandler = serviceStatusHandlerLiveData.value ?: return@observe
                    val updateValue =
                        if (serviceHandler.serviceCalled == ServiceCalled.COUNTER_INCREMENT_TIME) {
                            currentCounter.count + 1
                        } else {
                            currentCounter.count - 1
                        }

                    context?.let {
                        MaterialAlertDialogBuilder(it)
                            .setTitle(
                                it.getString(
                                    R.string.error_updating_counter_title,
                                    currentCounter.title,
                                    updateValue
                                )
                            )
                            .setMessage(it.getString(R.string.connection_error_description))
                            .setPositiveButton(it.getString(R.string.dismiss)) { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                }
            }

            showDeleteErrorViewLiveData.observe(viewLifecycleOwner) { condition ->
                if (condition) {
                    context?.let {
                        MaterialAlertDialogBuilder(it)
                            .setTitle(it.getString(R.string.error_deleting_counter_title))
                            .setMessage(it.getString(R.string.connection_error_description))
                            .setPositiveButton(it.getString(R.string.ok)) { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                }
            }
        }
        binding.searchBarView.searchToolbarIsDisplayedLiveData.observe(viewLifecycleOwner) { isDisplayed ->
            if (isDisplayed) {
                binding.fabAddCounter.visibility = View.GONE
                binding.transparentGrayView.visibility = View.VISIBLE
                showSoftKeyboard(binding.searchBarView.getEditText())
            } else {
                binding.fabAddCounter.visibility = View.VISIBLE
                binding.emptyMessageLayout.root.visibility = View.GONE
                binding.transparentGrayView.visibility = View.GONE
                hideSoftKeyboard()
            }
        }
    }

    private fun showList(counters: List<Counter>) {
        var finalList = counters
        if (binding.searchBarView.getEditText().text.isNotEmpty()) {
            finalList = getFilteredList(binding.searchBarView.getEditText().text.toString())
            if (finalList.isEmpty()) binding.emptyMessageLayout.root.visibility = View.VISIBLE
        }
        if (binding.searchBarView.searchToolbarIsDisplayedLiveData.value == false && finalList.none { it.isSelected }) {
            binding.fabAddCounter.visibility = View.VISIBLE
        }
        binding.rsvCounterList.showRecyclerAndTotalsViews(finalList)
        counterAdapter.replaceItems(finalList)
    }

    private fun setUpViews() {
        binding.refreshCounters.setColorSchemeResources(R.color.orange)
        binding.fabAddCounter.setOnClickListener {
            findNavController().navigate(
                CounterListFragmentDirections.actionHomeListToCreateCounter()
            )
        }
        binding.refreshCounters.setOnRefreshListener {
            binding.searchBarView.visibility = View.VISIBLE
            binding.customDeleteToolbar.root.visibility = View.GONE
            loadData()
        }
        binding.customDeleteToolbar.imgCloseToolbar.setOnClickListener {
            binding.searchBarView.visibility = View.VISIBLE
            binding.customDeleteToolbar.root.visibility = View.GONE
            val currentList = counterListViewModel.counterListLiveData.value
            val newList = currentList?.let { it.onEach { counter -> counter.isSelected = false } }
            showList(newList!!)
        }
        binding.customDeleteToolbar.imgDeleteToolbar.setOnClickListener {
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
                            counterListViewModel.deleteCounterList(countersSelectedList)
                            dialog.dismiss()
                        }
                        .setNegativeButton(it.getString(R.string.cancel)) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        }
        binding.searchBarView.getEditText().doOnTextChanged { text, _, _, _ ->
            val filteredList = getFilteredList(text.toString())
            if (filteredList.isEmpty()) {
                if (text.toString().isNotEmpty()) {
                    binding.emptyMessageLayout.root.visibility = View.VISIBLE
                }
            } else {
                binding.emptyMessageLayout.root.visibility = View.GONE
                counterAdapter.filterCountersList(filteredList)
                binding.rsvCounterList.setTotalsView(filteredList)
            }
            if (text.isNullOrEmpty() && (binding.searchBarView.searchToolbarIsDisplayedLiveData.value == true)) {
                binding.transparentGrayView.visibility = View.VISIBLE
            } else {
                binding.transparentGrayView.visibility = View.GONE
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
                binding.searchBarView.visibility = View.INVISIBLE
                binding.customDeleteToolbar.root.visibility = View.VISIBLE
                binding.fabAddCounter.visibility = View.GONE

                binding.customDeleteToolbar.txtToolbarDeleteTitle.text = context?.getString(
                    R.string.n_selected,
                    1
                )
            }

            override fun itemSelectionClicked(counters: List<Counter>) {
                binding.customDeleteToolbar.txtToolbarDeleteTitle.text = context?.getString(
                    R.string.n_selected,
                    counters.filter { it.isSelected }.size
                )
                if (counters.none { it.isSelected }) {
                    binding.searchBarView.visibility = View.VISIBLE
                    binding.customDeleteToolbar.root.visibility = View.GONE
                    if (binding.searchBarView.searchToolbarIsDisplayedLiveData.value == false) {
                        binding.fabAddCounter.visibility = View.VISIBLE
                    }
                }
            }

            override fun btnPlusClicked(counter: Counter) {
                counterListViewModel.incrementTime(counter)
            }

            override fun btnMinusClicked(counter: Counter) {
                counterListViewModel.decrementTime(counter)
            }
        })
    }

    private fun getFilteredList(text: String): MutableList<Counter> {
        val counterList = counterListViewModel.counterListLiveData.value
        val filteredList = mutableListOf<Counter>()
        counterList?.let {
            for (c in counterList) {
                if (c.title.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))) {
                    filteredList.add(c)
                }
            }
        }
        return filteredList
    }

    private fun loadData() {
        counterListViewModel.fetchCounterList()
    }
}
