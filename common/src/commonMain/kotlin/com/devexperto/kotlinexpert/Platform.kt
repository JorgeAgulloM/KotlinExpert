package com.devexperto.kotlinexpert

fun getAppTitle() = "My Notes - ${getPlatformName()}"

expect fun getPlatformName(): String
