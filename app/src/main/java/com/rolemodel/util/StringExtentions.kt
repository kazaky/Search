package com.rolemodel.util

fun String.upperCaseFirstLetter(): String {
    return this.replace(this[0],
            this[0].toUpperCase())
}