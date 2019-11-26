package com.rolemodel.data.model

import androidx.recyclerview.widget.DiffUtil

data class Person(
        val id: Int,
        val name: String?,
        val avatar: String?,
        val functions: List<String>?
)

class PersonDiffCallback : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }
}