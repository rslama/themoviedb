package com.slama.remote.utils

import org.junit.Assert.*

import org.junit.Test

class TimeUtilTest {

    @Test
    fun `when runtime is more than 0 then return proper formatted String`() {

        assertEquals("1h 0min", TimeUtil.minsToHours(60))
        assertEquals("2h 1min", TimeUtil.minsToHours(121))
    }

    @Test
    fun `when runtime is  0 then return proper formatted empty String`() {

        assertEquals("", TimeUtil.minsToHours(0))
    }
}