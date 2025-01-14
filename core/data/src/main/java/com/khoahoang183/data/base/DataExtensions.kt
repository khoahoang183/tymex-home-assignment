package com.khoahoang183.data.base

import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.Patterns
import com.khoahoang183.data.base.DateTimeCons.DATE_SERVER_FORMAT
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

fun <T> List<T>?.orMutableEmpty(): List<T> = this ?: ArrayList()

fun Date?.orEmpty(): Date = this ?: Date()

fun initDate(year: Int, month: Int, dayOfMonth: Int): Date {
    val iniCalendar = Calendar.getInstance()
    iniCalendar.set(Calendar.YEAR, year);  // Set the minimum year
    iniCalendar.set(Calendar.MONTH, month);  // Set the minimum month
    iniCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);  // Set the minimum day
    return return iniCalendar.time
}

fun String.Companion.empty() = ""

inline fun String?.ifNotNullOrEmpty(resolve: (s: String) -> Unit) {
    if (this != null && this.isEmpty().not()) resolve.invoke(this)
}

fun Long.Companion.noId() = -1L

fun Long.Companion.zero() = 0L

fun Double.Companion.zero() = 0.0

fun Long?.orNoId(): Long = this ?: Long.noId()

fun Long.isNoId(): Boolean = this == Long.noId()

fun Long.isNotNoId(): Boolean = this != Long.noId()

fun Long?.isNotNoId(): Boolean = this.orNoId() != Long.noId()

fun Long?.orZero(): Long = this ?: 0L

fun Boolean?.orFalse(): Boolean = this ?: false

fun Int?.orZero(): Int = this ?: 0

fun Int.Companion.zero() = 0

fun Float?.orZero(): Float = this ?: 0.0f

fun Double?.orZero(): Double = this ?: 0.0

fun Float?.roundUp(): Float {
    this ?: return 0f
    return (this * 100.0f).roundToInt() / 100.0f
}

val Long.formatThousand get() = "%,d".format(Locale.KOREA, this)

fun Float.string(): String {
    return if (this.compareTo(this.toInt()) == 0) {
        this.toInt().toString()
    } else toString()
}

fun String?.serverTimeToMobileTime(): String {
    return try {
        val sdfInput =
            SimpleDateFormat(
                DateTimeCons.HOUR_MINUTE_SECOND_FORMAT,
                Locale.getDefault()
            )
        val sdfOutput =
            SimpleDateFormat(DateTimeCons.HOUR_MINUTE_FORMAT, Locale.getDefault())
        val startTime = sdfInput.parse(this)
        val calendar = Calendar.getInstance()
        calendar.time = startTime

        sdfOutput.format(calendar.time)
    } catch (ex: Exception) {
        return ""
    }
}

fun String.isValidEmail(): Boolean {
    return if (TextUtils.isEmpty(this)) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}

fun String.isValidName(): Boolean {
    return length > 3
}

fun isStartDateEarlier(startDate: String, endDate: String,  format:String = "yyyy-MM-dd HH:mm:ss"): Boolean {
    val dateFormat = SimpleDateFormat(format)
    val start: Date = dateFormat.parse(startDate)
    val end: Date = dateFormat.parse(endDate)
    return start.before(end)
}

/**
 * convert date string server to other date string format
 */
@SuppressLint("SimpleDateFormat")
fun dateStringToNewFormat(
    dateString: String, // 2022-03-22T11:10:55.154Z
    outputPattern: String,
    serverPattern: String = DATE_SERVER_FORMAT,
): String {
    dateString.let {
        try {
//            val local = convertUTCToLocal(
//                dateString,
//                serverPattern,
//                serverPattern
//            )

            SimpleDateFormat(serverPattern).parse(dateString)?.let {
                return SimpleDateFormat(outputPattern).format(it)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
    return ""
}

