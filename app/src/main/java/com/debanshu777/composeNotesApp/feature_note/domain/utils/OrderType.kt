package com.debanshu777.composeNotesApp.feature_note.domain.utils

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
