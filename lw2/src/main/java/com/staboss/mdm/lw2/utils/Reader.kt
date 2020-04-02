package com.staboss.mdm.lw2.utils

import com.staboss.mdm.lw2.model.NetFlowData
import com.staboss.mdm.lw2.model.NetTime
import java.io.File

object Reader {
    const val txtFilePath = "src/main/resources/data.txt"
    const val csvFilePath = "src/main/resources/data.csv"
    const val pltFilePath = "src/main/resources/plot.csv"

    fun parseData() {
        val file = File(txtFilePath)
        val newFile = File(csvFilePath)
        val builder = StringBuilder()
        val fileData = file.readLines()
        var tmpBuilder = StringBuilder()

        fileData.forEachIndexed { index, line ->
            if (index != 0) {
                val lineData = line.replace("\\s+".toRegex(), " ").split(" ")
                tmpBuilder.append("${lineData[0]} ${lineData[1]}").append(',')
                tmpBuilder.append(lineData[2]).append(',')
                tmpBuilder.append(lineData[3]).append(',')
                tmpBuilder.append(lineData[4]).append(',')
                tmpBuilder.append(lineData[6]).append(',')
                tmpBuilder.append(lineData[7]).append(',')
                val tmp = lineData[9]
                var m = ""
                var f = ""
                if (tmp == "M") {
                    m = (lineData[8].toFloat() * 1024 * 1024).toString()
                    f = lineData[10]
                } else {
                    m = lineData[8]
                    f = lineData[9]
                }
                tmpBuilder.append(m).append(',')
                tmpBuilder.append(f)

                builder.append(tmpBuilder.toString()).append('\n')
                tmpBuilder = StringBuilder()
            } else {
                builder.append("Date first seen").append(',')
                builder.append("Duration").append(',')
                builder.append("Protocol").append(',')
                builder.append("Src IP Addr:Port").append(',')
                builder.append("Dst IP Addr:Port").append(',')
                builder.append("Packets").append(',')
                builder.append("Bytes").append(',')
                builder.append("Flows").append('\n')
            }
        }

        newFile.writeText(builder.toString())
    }

    fun readData(file: File): List<NetFlowData> {
        val resultNetFlowData = mutableListOf<NetFlowData>()
        val fileData = file.readLines()
        fileData.forEachIndexed() { index, line ->
            if (index != 0) {
                val lineData = line.split(",")
                if (lineData.size != 8) throw Exception("File has unsupported number of values (> 8)")

                val dateFirstSeen = NetTime(lineData[0])
                val duration = lineData[1].toFloat()
                val protocol = lineData[2]
                val srcIPAddr = lineData[3]
                val dstIPAddr = lineData[4]
                val packets = lineData[5].toInt()
                val bytes = lineData[6].toFloat()
                val flows = lineData[7].toInt()

                val newFlowData = NetFlowData(dateFirstSeen, duration, protocol, srcIPAddr, dstIPAddr, packets, bytes, flows)
                resultNetFlowData.add(newFlowData)
            }
        }
        return resultNetFlowData
    }
}