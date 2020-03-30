package com.staboss.mdm.lw1.model

import com.staboss.mdm.lw1.tariff.SMS
import com.staboss.mdm.lw1.tariff.Telephony

data class UserTariff(
        val userPhoneNum: String,
        val outgoingCall: Int,
        val incomingCall: Int,
        val smsCostPrice: Int,
        val totalFreeSms: Int? = null
) {

    fun calculateSMS(cdrData: List<CDRData>): Float {
        var smsPrice = 0F
        cdrData.forEach { session ->
            if (session.msisdn_origin.number == userPhoneNum) {
                var smsNumber = session.sms_number
                if (totalFreeSms != null && totalFreeSms > 0) {
                    smsNumber -= totalFreeSms
                }
                if (smsNumber > 0) {
                    val sms = SMS(smsNumber)
                    val price = sms.calculate(smsCostPrice)
                    smsPrice += price
                }
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
        return callsPrice
    }
}