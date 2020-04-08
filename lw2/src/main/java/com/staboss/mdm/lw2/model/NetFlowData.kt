package com.staboss.mdm.lw2.model

import java.text.SimpleDateFormat
import java.util.*

data class NetFlowData(
        val dateFirstSeen: NetTime,
        val duration: Float,
        val protocol: String,
        val srcIPAddr: String,
        val dstIPAddr: String,
        val packets: Int,
        val bytes: Float,
        val flows: Int
)

data class NetTime(private val time: String) {
    val timestamp: Date
        get() = SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S").parse(time)
    val stringFormat: String
        get() = SimpleDateFormat("hh:mm:ss.S").format(timestamp)
}