@file:Suppress("unused")

package com.wada811.immutablependingintent

import com.wada811.immutablependingintent.UseMutablePendingIntent.Reason

/**
 * Explain why to use MutablePendingIntent by selecting [Reason].
 * You can select from [UseMutablePendingIntent.Reason].
 */
@Retention(AnnotationRetention.BINARY)
@Target(
        AnnotationTarget.FUNCTION,
)
annotation class UseMutablePendingIntent(
        val reason: Reason,
        val otherReason: String = ""
) {
    /**
     * These reasons are from the below link.
     *
     * Create immutable pending intents whenever possible | Intents and Intent Filters | Android Developers
     * https://developer.android.com/guide/components/intents-filters#CreateImmutablePendingIntents
     */
    enum class Reason {
        /**
         * Supporting direct reply actions in notifications.
         * The direct reply requires a change to the clip data in the PendingIntent object that's associated with the reply.
         * Usually, you request this change by passing FILL_IN_CLIP_DATA as a flag to the fillIn() method.
         */
        DirectReplyActions,

        /**
         * Associating notifications with the Android Auto framework, using instances of CarAppExtender.
         */
        CarAppExtender,

        /**
         * Placing conversations in bubbles using instances of PendingIntent.
         * A mutable PendingIntent object allows the system to apply the correct flags,
         * such as FLAG_ACTIVITY_MULTIPLE_TASK and FLAG_ACTIVITY_NEW_DOCUMENT.
         */
        Bubbles,

        /**
         * Requesting device location information by calling requestLocationUpdates() or similar APIs.
         * The mutable PendingIntent object allows the system to add intent extras that represent location lifecycle events.
         * These events include a change in location and a provider becoming available.
         */
        LocationUpdates,

        /**
         * Scheduling alarms using AlarmManager.
         * The mutable PendingIntent object allows the system to add the EXTRA_ALARM_COUNT intent extra.
         * This extra represents the number of times that a repeating alarm has been triggered.
         * By containing this extra, the intent can accurately notify an app as to whether a repeating alarm was triggered multiple times,
         * such as when the device was asleep.
         */
        AlarmCount,

        /**
         * PendingIntent passed by [android.widget.RemoteViews.setPendingIntentTemplate] is overwritten by [android.widget.RemoteViews.setOnClickFillInIntent]
         * When using collections (eg. ListView, StackView etc.) in widgets,
         * it is very costly to set PendingIntents on the individual items, and is hence not recommended.
         * Instead this method should be used to set a single PendingIntent template on the collection,
         * and individual items can differentiate their on-click behavior using RemoteViews#setOnClickFillInIntent(int, Intent).
         */
        CollectionRemoteViews,

        /**
         * Other. You must explain by [otherReason] to your team members.
         */
        Other,
        ;
    }
}

