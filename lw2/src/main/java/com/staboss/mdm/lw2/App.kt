package com.staboss.mdm.lw2

import com.staboss.mdm.lw2.model.UserTariff
import com.staboss.mdm.lw2.utils.Plot
import com.staboss.mdm.lw2.utils.Utils
import java.io.File

val utils = Utils()

fun main() {
    println("Введите путь до файла с трафиком NetFlow формата .csv:")
    val path = utils.scanner.nextLine() // src/main/resources/data.csv
    val file = File(path)

    if (!file.exists()) {
        println("Файл не найден или не существует, попробуйте в другой раз.")
        return
    }

    println("\nВведите данные абонента для тарификации")

    var ipAddress: String   // 192.168.250.39
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

    var (k, freeKilobytes) = Pair(-1F, -1F) // (0.5, 1000)

    k = rwFloat("Тариф (Кб/руб)", k)
    freeKilobytes = rwFloat("Количество бесплатных килобайт", freeKilobytes)

    println()

    val user = UserTariff(ipAddress, k, freeKilobytes)
    val data = utils.reader.readData(file).filter { it.srcIPAddr.contains(user.ipAddress) }
    Plot.parseDataPlot(data.sortedBy { it.dateFirstSeen.timestamp })

    val totalPrice = user.calculateInternet(data)
    println("Тарификации абонента ${user.ipAddress}:" +
            "\n- тарификация услуг \"Интернет\" = $totalPrice руб.")
}

fun rwFloat(title: String, field: Float): Float {
    while (field == -1F) {
        try {
            print("$title: ")
            val tmp = utils.scanner.nextLine().toFloat()
            if (tmp < 0F) throw Exception()
            return tmp
        } catch (e: Exception) {
            println("Ошибка ввода, проверьте корректность введенных данных.")
        }
    }
    return 0F
}