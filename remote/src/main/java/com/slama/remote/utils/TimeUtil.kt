package com.slama.remote.utils

object TimeUtil {

    fun minsToHours(timeInMinutes: Int): String {
        val hours = timeInMinutes / 60
        val mins = timeInMinutes % 60
        return "${hours}h ${mins}min"
    }
}