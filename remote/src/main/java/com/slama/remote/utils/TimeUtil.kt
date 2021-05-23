package com.slama.remote.utils

object TimeUtil {

    fun minsToHours(timeInMinutes: Int): String {
        return if (timeInMinutes == 0) {
            ""
        } else {
            val hours = timeInMinutes / 60
            val mins = timeInMinutes % 60
            "${hours}h ${mins}min"
        }
    }
}