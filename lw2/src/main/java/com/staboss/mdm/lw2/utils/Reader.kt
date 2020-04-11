package com.staboss.mdm.lw2.utils

import com.staboss.mdm.lw2.model.NetFlowData
import com.staboss.mdm.lw2.model.NetTime
import java.io.File

object Reader {
    lateinit var plotFilePath: String

    fun parseData(filePath: String, csvFilePath: String) {
        val file = File(filePath)
        val newFile = File(csvFilePath)
        val fileData = file.readLines()

        val text = buildString {
            fileData.forEachIndexed { index, line ->
                if (index != 0) {
                    val lineData = line.replace("\\s+".toRegex(), " ").split(" ")
                    append("${lineData[0]} ${lineData[1]}").append(',')
                    append("${lineData[2]},")
                    append("${lineData[3]},")
                    append("${lineData[4]},")
                    append("${lineData[6]},")
                    append("${lineData[7]},")
                    val tmp = lineData[9]
                    val (m, f) = when (tmp) {
                        "M" -> (lineData[8].toFloat() * 1024 * 1024).toString() to lineData[10]
                        else -> lineData[8] to lineData[9]
                    }
                    append("$m,$f\n")
                } else {
                    append("Date first seen,Duration,Protocol,Src IP Addr:Port,Dst IP Addr:Port,Packets,Bytes,Flows\n")
                }
            }
        }

        newFile.writeText(text)
    }

    fun readData(file: File): List<NetFlowData> {
        val resultNetFlowData = mutableListOf<NetFlowData>()
        val fileData = file.readLines()
        fileData.forEachIndexed { index, line ->
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

                // we are sure that received data is correct
                val newFlowData = NetFlowData(dateFirstSeen, duration, protocol, srcIPAddr, dstIPAddr, packets, bytes, flows)
                resultNetFlowData.add(newFlowData)
            }
        }
        return resultNetFlowData
    }
}