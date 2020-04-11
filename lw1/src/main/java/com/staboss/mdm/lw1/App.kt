package com.staboss.mdm.lw1

import com.staboss.mdm.lw1.model.CDRData
import com.staboss.mdm.lw1.model.UserTariff
import com.staboss.mdm.lw1.utils.Utils
import java.io.File

val utils = Utils()
/*
- Путь до файла CDR = src/main/resources/data.csv
- Номер = 915783624
- Входящие звонки = 0
- Исходящие звонки = 1
- Количество бесплатных минут = 10
- Стоимость отправки одного СМС = 5
*/
fun main() {
    println("Введите путь до файла CDR (Call Detail Record) формата .csv:")
    val path = utils.scanner.nextLine()
    val file = File(path)

    if (!file.exists()) {
        println("Файл не найден или не существует, проверьте введенный путь до файла.")
        return
    }

    println("\nВведите данные абонента для тарификации")

    var userPhoneNum: String
    while (true) {
        try {
            print("Номер: ")
            val tmp = utils.scanner.nextLine()
            if (tmp.isEmpty() || tmp.isBlank()) throw Exception()
            userPhoneNum = tmp
            break
        } catch (e: Exception) {
            println("Ошибка ввода, проверьте корректность введенных данных.")
        }
    }

    val incomingCall = getNumber("Входящие звонки (руб/мин)")
    val outgoingCall = getNumber("Исходящие звонки (руб/мин)")

    val freeMinutes = getNumber("Количество бесплатных минут")
    val smsCostPrice = getNumber("Стоимость отправки одного СМС")

    println()

    val userTariff = UserTariff(userPhoneNum, outgoingCall, incomingCall, smsCostPrice, freeMinutes)
    val data: List<CDRData> = utils.reader.readData(file)

    if (data.isEmpty()) {
        println("Абонент с номером $userPhoneNum не найден.")
        return
    }

    val tariffSms = String.format("%.2f", userTariff.calculateSMS(data)).replace(",", ".")
    val tariffCalls = String.format("%.2f", userTariff.calculateCalls(data)).replace(",", ".")

    println("Тарификации абонента ${userTariff.userPhoneNum}:" +
            "\n- тарификация услуг \"Телефония\" = $tariffCalls руб." +
            "\n- тарификация услуг \"СМС\" = $tariffSms руб."
    )
}

fun getNumber(title: String): Int {
    while (true) {
        try {
            print("$title: ")
            val tmp = utils.scanner.nextLine().toInt()
            if (tmp < 0) throw Exception()
            return tmp
        } catch (e: Exception) {
            println("Ошибка ввода, проверьте корректность введенных данных.")
        }
    }
}