package com.staboss.mdm.lw1.tariff

interface Tariff {
    fun calculate(k: Int): Float
}

class SMS(private val totalNumberOfSms: Int): Tariff {
    override fun calculate(k: Int): Float = totalNumberOfSms * k.toFloat()
}

class Telephony(private val totalDurationOfCalls: Float): Tariff {
    override fun calculate(k: Int): Float = totalDurationOfCalls * k
}