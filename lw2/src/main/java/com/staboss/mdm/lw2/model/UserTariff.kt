package com.staboss.mdm.lw2.model

data class UserTariff(val ipAddress: String, val k: Float, val freeKilobytes: Float = 0F) {
    fun calculateInternet(netFlowData: List<NetFlowData>): Float {
        var totalBytes = 0F
        netFlowData.forEach { flowData ->
            if (flowData.srcIPAddr.contains(ipAddress)) {
                totalBytes += flowData.bytes
            }
        }
        totalBytes /= 1024
        totalBytes -= freeKilobytes
        return if (totalBytes < 0) 0F else totalBytes * k
    }
}