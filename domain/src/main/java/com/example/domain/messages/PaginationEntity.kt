package com.example.domain.messages

data class PaginationEntity<I : Any>(
    val totalItems: Int,
    val countOfPages: Int,
    val itemsData: List<I>
)