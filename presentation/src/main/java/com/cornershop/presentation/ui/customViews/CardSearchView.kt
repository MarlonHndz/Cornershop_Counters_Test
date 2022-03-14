package com.cornershop.presentation.ui.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cornershop.presentation.R
import com.cornershop.presentation.databinding.SearchBarLayoutBinding
import com.cornershop.presentation.databinding.SearchCardLayoutBinding
import com.cornershop.presentation.databinding.SearchToolbarLayoutBinding

class CardSearchView constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    private val binding: SearchBarLayoutBinding

    private val cardSearchBinding: SearchCardLayoutBinding
    private val toolbarSearchBinding: SearchToolbarLayoutBinding

    private val _searchToolbarIsDisplayed: MutableLiveData<Boolean> = MutableLiveData()

    val searchToolbarIsDisplayedLiveData: LiveData<Boolean>
        get() = _searchToolbarIsDisplayed

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.search_bar_layout, this, true)

        cardSearchBinding = binding.cardLayout
        toolbarSearchBinding = binding.toolbarLayout

        binding.root.setOnClickListener { showSearchView() }

        toolbarSearchBinding.imgBackArrow.setOnClickListener { searchBackArrow() }

        toolbarSearchBinding.imgClearSearchText.setOnClickListener { clearEditText() }
    }

    private fun showSearchView() {
        cardSearchBinding.root.visibility = View.INVISIBLE
        toolbarSearchBinding.root.visibility = View.VISIBLE
        clearEditText()
        toolbarSearchBinding.edtTxtSearch.requestFocus()
        searchToolbarIsDisplayed(true)
    }

    private fun searchBackArrow() {
        cardSearchBinding.root.visibility = View.VISIBLE
        toolbarSearchBinding.root.visibility = View.GONE
        clearEditText()
        searchToolbarIsDisplayed(false)
    }

    private fun clearEditText() {
        toolbarSearchBinding.edtTxtSearch.text.clear()
    }

    fun getEditText() = toolbarSearchBinding.edtTxtSearch

    private fun searchToolbarIsDisplayed(isDisplayed: Boolean) {
        _searchToolbarIsDisplayed.postValue(isDisplayed)
    }
}
