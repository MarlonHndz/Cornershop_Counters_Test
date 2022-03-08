package com.cornershop.presentation.ui.counterList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cornershop.domain.models.Counter
import com.cornershop.presentation.databinding.CounterItemBinding

class CounterAdapter : RecyclerView.Adapter<CounterAdapter.ViewHolder>() {

    private var items = mutableListOf<Counter>()
    private var listener: Listener? = null

    inner class ViewHolder(private val binding: CounterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(counter: Counter) {
            with(binding) {
                // Bind
                txtCounterTitle.text = counter.title
                txtCounterTimes.text = counter.count.toString()
            }
        }
    }

    interface Listener {
        fun itemClicked(counter: Counter)
    }

    fun addListener(listener: Listener) {
        this.listener = listener
    }

    fun replaceItems(listCounters: List<Counter>) {
        items.clear()
        items.addAll(listCounters)
        notifyDataSetChanged()
    }

    fun removeAllItems() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CounterItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}
