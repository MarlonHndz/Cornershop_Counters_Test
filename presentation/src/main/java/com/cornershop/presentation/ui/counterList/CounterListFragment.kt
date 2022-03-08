package com.cornershop.presentation.ui.counterList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cornershop.domain.models.Counter
import com.cornershop.presentation.R
import com.cornershop.presentation.databinding.CounterListFragmentBinding
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CounterListFragment : Fragment() {

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
        setUpObservers()
        setUpViews()
        setUpListenersFromView()
        setUpCountersRecyclerView()
        loadData()
    }

    private fun setUpObservers() {
        counterListViewModel.counterList.observe(viewLifecycleOwner) { counters ->
            binding.refreshCounters.isRefreshing = false
            setUpItemsAndTimes(counters)
            counterAdapter.replaceItems(counters)
        }
    }

    private fun setUpItemsAndTimes(counters: List<Counter>) {
        with(binding) {
            txtTotalCounters.text = activity?.getString(
                R.string.n_items,
                counters.size
            )

            txtTotalTimes.text = activity?.getString(
                R.string.n_times,
                counters.map { it.count }.sum()
            )
        }
    }

    private fun setUpViews() {
        // SetUp Views
        binding.fabAddCounter.setOnClickListener {
            // Navigate to add counter
        }
    }

    private fun setUpListenersFromView() {
        binding.refreshCounters.setOnRefreshListener {
            counterListViewModel.fetchCounterList()
        }
    }

    private fun setUpCountersRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this.context)
        binding.rvCounterList.apply {
            layoutManager = linearLayoutManager
            adapter = counterAdapter
        }

        counterAdapter.addListener(object : CounterAdapter.Listener {
            override fun itemClicked(counter: Counter) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun loadData() {
        counterListViewModel.fetchCounterList()
    }
}
