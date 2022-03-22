package com.wada811.immutablependingintent.test

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle

class RawPendingIntentKotlin {
    fun getActivity(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent {
        return PendingIntent.getActivity(context, requestCode, intent, flags)
    }

    fun getActivity(context: Context, requestCode: Int, intent: Intent, flags: Int, options: Bundle?): PendingIntent {
        return PendingIntent.getActivity(context, requestCode, intent, flags, options)
    }

    fun getActivities(context: Context, requestCode: Int, intents: Array<Intent>, flags: Int): PendingIntent {
        return PendingIntent.getActivities(context, requestCode, intents, flags)
    }

    fun getActivities(context: Context, requestCode: Int, intents: Array<Intent>, flags: Int, options: Bundle?): PendingIntent {
        return PendingIntent.getActivities(context, requestCode, intents, flags, options)
    }

    fun getBroadcast(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent {
        return PendingIntent.getBroadcast(context, requestCode, intent, flags)
    }

    fun getService(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent {
        return PendingIntent.getService(context, requestCode, intent, flags)
    }

    fun getForegroundService(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent {
        return PendingIntent.getForegroundService(context, requestCode, intent, flags)
    }
}

