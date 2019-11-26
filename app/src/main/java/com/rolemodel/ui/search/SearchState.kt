package com.rolemodel.ui.search

import com.rolemodel.data.model.Person

sealed class SearchState {
    object ShowInitial : SearchState()
    class ShowPersons(val persons: List<Person>) : SearchState()
    object ShowEmpty : SearchState()
}