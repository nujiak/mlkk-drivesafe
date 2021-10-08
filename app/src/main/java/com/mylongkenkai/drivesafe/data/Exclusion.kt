package com.mylongkenkai.drivesafe.data

data class Exclusion(
    val phoneNumber : Int
) {
    override fun toString(): String = phoneNumber.toString()
}