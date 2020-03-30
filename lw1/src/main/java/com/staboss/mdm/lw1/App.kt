package com.staboss.mdm.lw1

import com.staboss.mdm.lw1.model.CDRData
import com.staboss.mdm.lw1.model.UserTariff
import com.staboss.mdm.lw1.utils.Utils
import java.io.File

val utils = Utils()

fun main() {
    println("Введите путь до файла CDR (Call Detail Record) формата .csv:")
    val path = utils.scanner.nextLine() // src/main/resources/data.csv
    val file = File(path)

    if (!file.exists()) {
        println("Файл не найден или не существует, попробуйте в другой раз.")
        return
    }

    println("\nВведите данные абонента для тарификации")

    var phone: String   // 915783624
    while (true) {
        try {
            print("Номер: ")
            val tmp = utils.scanner.nextLine()
            if (tmp.isEmpty() || tmp.isBlank()) throw Exception()
            phone = tmp
            break
        } catch (e: Exception) {
            println("Ошибка ввода, проверьте корректность введенных данных.")
        }
    }

    var (kIn, kOut) = Pair(-1, -1)  // (0, 2)
    var (nSms, kSms) = Pair(-1, -1) // (10, 0)

    kIn = rwInt("Входящие звонки (руб/мин)", kIn)
    kOut = rwInt("Исходящие звонки (руб/мин)", kOut)

    nSms = rwInt("Количество бесплатных СМС", nSms)
    kSms = rwInt("Стоимость отправки одного СМС", kSms)

    println()

    val userTariff = UserTariff(phone, kOut, kIn, kSms, nSms)
    val data: List<CDRData> = utils.reader.readData(file).filter { cdrData ->
        cdrData.msisdn_origin.number == userTariff.userPhoneNum || cdrData.msisdn_dest.number == userTariff.userPhoneNum
    }

    if (data.isEmpty()) {
        println("Абонент с номером $phone не найден.")
        return
    }

    val tariffSms = userTariff.calculateSMS(data)
    val tariffCalls = userTariff.calculateCalls(data)

    println("Тарификации абонента ${userTariff.userPhoneNum}:" +
            "\n- тарификации услуг \"Телефония\" = $tariffCalls руб." +
            "\n- тарификации услуг \"СМС\" = $tariffSms руб."
    )
}

fun rwInt(title: String, field: Int): Int {
    while (field == -1) {
        try {
            print("$title: ")
            val tmp = utils.scanner.nextLine().toInt()
            if (tmp < 0) throw Exception()
            return tmp
        } catch (e: Exception) {
            println("Ошибка ввода, проверьте корректность введенных данных.")
        }
    }
    return 0
}