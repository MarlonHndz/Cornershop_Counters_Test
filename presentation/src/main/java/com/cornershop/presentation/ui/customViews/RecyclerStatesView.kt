package com.cornershop.presentation.ui.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cornershop.domain.models.Counter
import com.cornershop.presentation.R
import com.cornershop.presentation.databinding.RecyclerEmptyLayoutBinding
import com.cornershop.presentation.databinding.RecyclerErrorLayoutBinding
import com.cornershop.presentation.databinding.RecyclerLoadingLayoutBinding
import com.cornershop.presentation.databinding.RecyclerStatesLceLayoutBinding
import com.cornershop.presentation.databinding.RecyclerTotalsLayoutBinding

class RecyclerStatesView constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    private val binding: RecyclerStatesLceLayoutBinding

    private val errorBinding: RecyclerErrorLayoutBinding
    private val emptyBinding: RecyclerEmptyLayoutBinding
    private val loadingBinding: RecyclerLoadingLayoutBinding
    private val totalsBinding: RecyclerTotalsLayoutBinding

    val recyclerView: RecyclerView
        get() = binding.customRecyclerView

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.recycler_states_lce_layout, this, true)

        errorBinding = binding.customErrorView
        emptyBinding = binding.customEmptyView
        loadingBinding = binding.customLoadingView
        totalsBinding = binding.customTotalsView
    }

    fun showEmptyView() {
        recyclerView.visibility = View.GONE
        loadingBinding.root.visibility = View.GONE
        errorBinding.root.visibility = View.GONE
        totalsBinding.root.visibility = View.GONE

        emptyBinding.root.visibility = View.VISIBLE
    }

    fun showErrorView() {
        recyclerView.visibility = View.GONE
        loadingBinding.root.visibility = View.GONE
        emptyBinding.root.visibility = View.GONE
        totalsBinding.root.visibility = View.GONE

        errorBinding.root.visibility = View.VISIBLE
    }

    fun showLoadingView() {
        recyclerView.visibility = View.GONE
        emptyBinding.root.visibility = View.GONE
        errorBinding.root.visibility = View.GONE
        totalsBinding.root.visibility = View.GONE

        loadingBinding.root.visibility = View.VISIBLE
    }

    private fun showRecyclerView() {
        loadingBinding.root.visibility = View.GONE
        errorBinding.root.visibility = View.GONE
        emptyBinding.root.visibility = View.GONE

        recyclerView.visibility = View.VISIBLE
        totalsBinding.root.visibility = View.VISIBLE
    }

    fun showRecyclerAndTotalsViews(counters: List<Counter>) {
        if (counters.isNotEmpty()) {
            showRecyclerView()
            setTotalsView(counters)
        }
    }

    fun setTotalsView(counters: List<Counter>) {
        with(totalsBinding) {
            txtTotalCounters.text = context?.getString(
                R.string.n_items,
                counters.size
            )
            txtTotalTimes.text = context?.getString(
                R.string.n_times,
                counters.map { it.count }.sum()
            )
        }
    }

    fun setOnRetryClickListener(callback: () -> Unit) {
        errorBinding.txBtnErrorRetry.setOnClickListener {
            callback()
        }
    }
}
