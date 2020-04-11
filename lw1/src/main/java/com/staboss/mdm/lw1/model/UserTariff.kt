package com.staboss.mdm.lw1.model

import com.staboss.mdm.lw1.utils

data class UserTariff(
        val userPhoneNum: String,
        val outgoingCall: Int,
        val incomingCall: Int,
        val smsCostPrice: Int,
        val freeMinutes: Int = 0
) {

    fun calculateSMS(cdrData: List<CDRData>): Double {
        return cdrData
                .filter { data -> data.msisdn_origin.number == userPhoneNum }
                .map { data -> utils.tariff.calculateSMS(data.sms_number, smsCostPrice) }
                .sum()
    }

    fun calculateCalls(cdrData: List<CDRData>): Double {
        val sum = cdrData
                .map { data ->
                    when (userPhoneNum) {
                        data.msisdn_origin.number -> utils.tariff.calculateCalls(data.call_duration, outgoingCall)
                        data.msisdn_dest.number -> utils.tariff.calculateCalls(data.call_duration, incomingCall)
                        else -> 0.0
                    }
                }
                .sum()

        return if (freeMinutes != 0) sum - outgoingCall * freeMinutes else sum
    }
}