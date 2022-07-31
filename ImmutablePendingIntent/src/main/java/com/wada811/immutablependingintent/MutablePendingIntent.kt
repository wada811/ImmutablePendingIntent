@file:JvmName("MutablePendingIntent")

package com.wada811.immutablependingintent

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle

/**
 * This class wraps [android.app.PendingIntent] and convert the passed `flags` parameter to mutable.
 *
 * https://developer.android.com/about/versions/12/behavior-changes-12#pending-intent-mutability
 */
object MutablePendingIntent {
    internal val FLAG_MUTABLE: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE else 0
    private fun Int.toMutable(): Int {
        return this or FLAG_MUTABLE and ImmutablePendingIntent.FLAG_IMMUTABLE.inv()
    }

    @JvmStatic
    fun getActivity(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent {
        return PendingIntent.getActivity(context, requestCode, intent, flags.toMutable())
    }

    @JvmStatic
    fun getActivity(context: Context, requestCode: Int, intent: Intent, flags: Int, bundle: Bundle?): PendingIntent {
        return PendingIntent.getActivity(context, requestCode, intent, flags.toMutable(), bundle)
    }

    @JvmStatic
    fun getActivities(context: Context, requestCode: Int, intents: Array<Intent>, flags: Int): PendingIntent {
        return PendingIntent.getActivities(context, requestCode, intents, flags.toMutable())
    }

    @JvmStatic
    fun getActivities(context: Context, requestCode: Int, intents: Array<Intent>, flags: Int, bundle: Bundle?): PendingIntent {
        return PendingIntent.getActivities(context, requestCode, intents, flags.toMutable(), bundle)
    }

    @JvmStatic
    fun getBroadcast(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent {
        return PendingIntent.getBroadcast(context, requestCode, intent, flags.toMutable())
    }

    @JvmStatic
    fun getService(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent {
        return PendingIntent.getService(context, requestCode, intent, flags.toMutable())
    }

    @JvmStatic
    fun getForegroundService(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PendingIntent.getForegroundService(context, requestCode, intent, flags.toMutable())
        } else {
            PendingIntent.getService(context, requestCode, intent, flags.toMutable())
        }
    }
}
