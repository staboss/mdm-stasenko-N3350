package com.staboss.mdm.lw1.utils

object Tariff {
    fun calculateSMS(totalNumberOfSms: Int, k: Int): Double = totalNumberOfSms * k.toDouble()

    fun calculateCalls(totalDurationOfCalls: Double, k: Int): Double = totalDurationOfCalls * k.toDouble()
}