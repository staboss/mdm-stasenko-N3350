package com.staboss.mdm.lw2

import com.staboss.mdm.lw2.model.UserTariff
import com.staboss.mdm.lw2.utils.Plot
import com.staboss.mdm.lw2.utils.Utils
import java.io.File

val utils = Utils()
/*
- Путь до файла с трафиком NetFlow = src/main/resources/data.csv
- IP-адресс = 192.168.250.39
- Тариф = 0.5
- Количество бесплатных килобайт = 1000
*/
fun main() {
    println("Введите путь до файла с трафиком NetFlow формата .csv:")
    val path = utils.scanner.nextLine()
    val file = File(path)

    if (!file.exists()) {
        println("Файл не найден или не существует, попробуйте в другой раз.")
        return
    }

    utils.reader.plotFilePath = file.absolutePath.substring(0, file.absolutePath.lastIndexOf('/')) + "/plot.csv"

    println("\nВведите данные абонента для тарификации")

    var ipAddress: String
    while (true) {
        try {
            print("IP-адресс: ")
            val tmp = utils.scanner.nextLine()
            if (tmp.isEmpty() || tmp.isBlank()) throw Exception()
            ipAddress = tmp
            break
        } catch (e: Exception) {
            println("Ошибка ввода, проверьте корректность введенных данных.")
        }
    }

    val k = getNumber("Тариф (Кб/руб)")
    val freeKilobytes = getNumber("Количество бесплатных килобайт")

    println()

    val user = UserTariff(ipAddress, k, freeKilobytes)
    val data = utils.reader.readData(file)
    Plot.parseDataPlot(data.sortedBy { it.dateFirstSeen.timestamp })

    val totalPrice = String.format("%.2f", user.calculateInternet(data)).replace(",", ".")
    println("Тарификации абонента ${user.ipAddress}:" +
            "\n- тарификация услуг \"Интернет\" = $totalPrice руб.")
}

fun getNumber(title: String): Float {
    while (true) {
        try {
            print("$title: ")
            val tmp = utils.scanner.nextLine().toFloat()
            if (tmp < 0F) throw Exception()
            return tmp
        } catch (e: Exception) {
            println("Ошибка ввода, проверьте корректность введенных данных.")
        }
    }
}