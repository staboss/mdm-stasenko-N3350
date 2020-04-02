package com.staboss.mdm.lw2.utils

import com.staboss.mdm.lw2.model.NetFlowData
import com.staboss.mdm.lw2.utils
import java.io.File

object Plot {
    fun parseDataPlot(netFlowData: List<NetFlowData>) {
        val newFile = File(utils.reader.pltFilePath)
        val builder = StringBuilder()

        builder.append("\"dateTime\"").append(',')
        builder.append("\"dataTraffic\"").append('\n')

        netFlowData.forEach { flowData ->
            builder.append("\"2020-02-25T${flowData.dateFirstSeen.stringFormat}Z\"").append(',')
            builder.append("\"${flowData.bytes}\"").append('\n')
        }

        newFile.writeText(builder.toString())
    }
}