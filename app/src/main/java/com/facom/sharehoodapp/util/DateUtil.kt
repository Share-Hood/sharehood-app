package com.facom.sharehoodapp.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * CREDITS FOR THE AUTHOR
 * Author: Nick Russler: https://github.com/nickrussler
 * From: https://gist.github.com/nickrussler/7527851
 */

class DateUtil {

    companion object {
        fun toISO8601UTC(date: Date): String {
            val tz = TimeZone.getTimeZone("UTC")
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'")
            df.setTimeZone(tz)
            return df.format(date)
        }

        fun fromISO8601UTC(dateStr: String): Date? {
            val tz = TimeZone.getTimeZone("UTC")
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'")
            df.setTimeZone(tz)

            try {
                return df.parse(dateStr)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return null
        }

        fun toBRFormat(date: Date?): String {
            val df = SimpleDateFormat("dd/MM/yyyy")
            return df.format(date)
        }
    }

}