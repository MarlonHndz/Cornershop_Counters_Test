package com.cornershop.presentation.ui.counterList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cornershop.domain.models.Counter
import com.cornershop.presentation.databinding.CounterItemBinding

class CounterAdapter : RecyclerView.Adapter<CounterAdapter.MyViewHolder>() {

    private var items = mutableListOf<Counter>()
    private var listener: Listener? = null

    inner class MyViewHolder(private val binding: CounterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(counter: Counter, position: Int) {
            with(binding) {
                txtCounterTitle.text = counter.title
                txtCounterTimes.text = counter.count.toString()

                // Handling view
                if (items.any { it.isSelected }) {
                    groupCounterButtons.visibility = View.GONE
                    if (counter.isSelected) {
                        groupSelectedView.visibility = View.VISIBLE
                    } else {
                        groupSelectedView.visibility = View.GONE
                    }
                } else {
                    groupCounterButtons.visibility = View.VISIBLE
                    groupSelectedView.visibility = View.GONE
                }

                // Handling listeners
                if (items.any { it.isSelected }) {
                    root.setOnClickListener {
                        items[position].isSelected = !counter.isSelected
                        notifyItemRangeChanged(0, items.size)
                        listener?.itemSelectionClicked(items)
                    }
                } else {
                    root.setOnClickListener {}
                }

                root.setOnLongClickListener {
                    items[position].isSelected = true
                    notifyItemRangeChanged(0, items.size)
                    listener?.itemLongClicked(items)
                    true
                }
            }
        }
    }

    interface Listener {
        fun itemLongClicked(counters: List<Counter>)
        fun itemSelectionClicked(counters: List<Counter>)
    }

    fun addListener(listener: Listener) {
        this.listener = listener
    }

    fun replaceItems(listCounters: List<Counter>) {
        items.clear()
        items.addAll(listCounters)
        notifyItemRangeChanged(0, items.size)
    }

    fun removeAllItems() {
        items.clear()
        notifyItemRangeChanged(0, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CounterItemBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.bind(items[position], position)

    override fun getItemCount(): Int = items.size
}
