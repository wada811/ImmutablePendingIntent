@file:JvmName("ImmutablePendingIntent")

package android.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle

/**
 * This class wraps [android.app.PendingIntent] and convert the passed `flags` parameter to immutable.
 *
 * https://developer.android.com/about/versions/12/behavior-changes-12#pending-intent-mutability
 */
@SuppressLint("UseRawPendingIntent") // suppress lint for testing
object ImmutablePendingIntent {
    internal val FLAG_IMMUTABLE: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
    private fun Int.toImmutable(): Int {
        return this or FLAG_IMMUTABLE and MutablePendingIntent.FLAG_MUTABLE.inv()
    }

    /**
     * Wrap [android.app.PendingIntent#getActivity] and convert [flags] to immutable.
     */
    @JvmStatic
    fun getActivity(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent {
        return PendingIntent.getActivity(context, requestCode, intent, flags.toImmutable())
    }

    /**
     * Wrap [android.app.PendingIntent#getActivity] and convert [flags] to immutable.
     */
    @JvmStatic
    fun getActivity(context: Context, requestCode: Int, intent: Intent, flags: Int, bundle: Bundle?): PendingIntent {
        return PendingIntent.getActivity(context, requestCode, intent, flags.toImmutable(), bundle)
    }

    @JvmStatic
    fun getActivities(context: Context, requestCode: Int, intents: Array<Intent>, flags: Int): PendingIntent {
        return PendingIntent.getActivities(context, requestCode, intents, flags.toImmutable())
    }

    @JvmStatic
    fun getActivities(context: Context, requestCode: Int, intents: Array<Intent>, flags: Int, bundle: Bundle?): PendingIntent {
        return PendingIntent.getActivities(context, requestCode, intents, flags.toImmutable(), bundle)
    }

    @JvmStatic
    fun getBroadcast(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent {
        return PendingIntent.getBroadcast(context, requestCode, intent, flags.toImmutable())
    }

    @JvmStatic
    fun getService(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent {
        return PendingIntent.getService(context, requestCode, intent, flags.toImmutable())
    }

    @JvmStatic
    fun getForegroundService(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PendingIntent.getForegroundService(context, requestCode, intent, flags.toImmutable())
        } else {
            PendingIntent.getService(context, requestCode, intent, flags.toImmutable())
        }
    }
}
