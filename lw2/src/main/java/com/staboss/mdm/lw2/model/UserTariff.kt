package com.staboss.mdm.lw2.model

data class UserTariff(val ipAddress: String, val k: Float, val freeKilobytes: Float = 0F) {
    fun calculateInternet(netFlowData: List<NetFlowData>): Float {
        val totalBytes = netFlowData
                .filter { flowData -> flowData.srcIPAddr.contains(ipAddress) }
                .map { data -> data.bytes }
                .sum() / 1024 - freeKilobytes
        return if (totalBytes < 0) 0F else totalBytes * k
    }
}