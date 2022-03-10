package com.cornershop.domain.models

data class Counter(
    val id: String,
    val title: String,
    val count: Int,
    var isSelected: Boolean
)
