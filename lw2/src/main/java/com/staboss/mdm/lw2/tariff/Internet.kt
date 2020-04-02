package com.staboss.mdm.lw2.tariff

class Internet(private val totalBytes: Int) {
    fun calculate(k: Float): Float = totalBytes * k
}