package com.staboss.mdm.lw2.utils

import com.staboss.mdm.lw2.model.NetFlowData
import com.staboss.mdm.lw2.utils
import java.io.File

object Plot {
    fun parseDataPlot(netFlowData: List<NetFlowData>) {
        val newFile = File(utils.reader.plotFilePath)

        val text = buildString {
            append("\"dateTime\",\"dataTraffic\"\n")
            netFlowData.forEach { data ->
                append("\"2020-02-25T${data.dateFirstSeen.stringFormat}Z\",\"${data.bytes}\"\n")
            }
        }

        newFile.writeText(text)
    }
}