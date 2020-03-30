package com.staboss.mdm.lw1.model

import java.text.SimpleDateFormat
import java.util.*

data class CDRData(
        val timestamp: CallTime,
        val msisdn_origin: MobileNumber,
        val msisdn_dest: MobileNumber,
        val call_duration: Double,
        val sms_number: Int
)

data class MobileNumber(val number: String)

data class CallTime(private val time: String) {
    val timestamp: Date
        get() = SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(time)
}