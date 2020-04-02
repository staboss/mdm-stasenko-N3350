package com.staboss.mdm.lw1.model

import com.staboss.mdm.lw1.tariff.SMS
import com.staboss.mdm.lw1.tariff.Telephony

data class UserTariff(
        val userPhoneNum: String,
        val outgoingCall: Int,
        val incomingCall: Int,
        val smsCostPrice: Int,
        val freeMinutes: Int = 0
) {

    fun calculateSMS(cdrData: List<CDRData>): Float {
        var smsPrice = 0F
        cdrData.forEach { session ->
            if (session.msisdn_origin.number == userPhoneNum) {
                val sms = SMS(session.sms_number)
                val price = sms.calculate(smsCostPrice)
                smsPrice += price
            }

        }
        return smsPrice
    }

    fun calculateCalls(cdrData: List<CDRData>): Float {
        var callsPrice = 0F
        cdrData.forEach { session ->
            val telephony = Telephony(session.call_duration.toFloat())
            val price = when (userPhoneNum) {
                session.msisdn_origin.number -> telephony.calculate(outgoingCall)
                session.msisdn_dest.number -> telephony.calculate(incomingCall)
                else -> 0F
            }
            callsPrice += price
        }
        if (freeMinutes != 0) {
            callsPrice -= outgoingCall * freeMinutes
        }
        return callsPrice
    }
}