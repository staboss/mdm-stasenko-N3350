package com.staboss.mdm.lw1.utils

import com.staboss.mdm.lw1.model.CDRData
import com.staboss.mdm.lw1.model.CallTime
import com.staboss.mdm.lw1.model.MobileNumber
import java.io.File

class Reader {
    fun readData(file: File): List<CDRData> {
        val resultCDRData = mutableListOf<CDRData>()
        val fileData = file.readLines()
        fileData.forEachIndexed() { index, line ->
            if (index != 0) {
                val lineData = line.split(",")
                if (lineData.size != 5) throw Exception("File has unsupported number of values (> 5)")

                val callTime = CallTime(lineData[0])
                val msisdnOrigin = MobileNumber(lineData[1])
                val msisdnDest = MobileNumber(lineData[2])
                val callDuration = lineData[3].toDouble()
                val smsNumber = lineData[4].toInt()

                val cdrUser = CDRData(callTime, msisdnOrigin, msisdnDest, callDuration, smsNumber)
                resultCDRData.add(cdrUser)
            }
        }
        return resultCDRData
    }
}