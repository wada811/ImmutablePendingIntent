package com.wada811.immutablependingintent.test;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

class RawPendingIntentJava {
    public PendingIntent getActivity(Context context, int requestCode, Intent intent, int flags) {
        return PendingIntent.getActivity(context, requestCode, intent, flags);
    }

    public PendingIntent getActivity(Context context, int requestCode, Intent intent, int flags, Bundle options) {
        return PendingIntent.getActivity(context, requestCode, intent, flags, options);
    }

    public PendingIntent getActivities(Context context, int requestCode, Intent[] intents, int flags) {
        return PendingIntent.getActivities(context, requestCode, intents, flags);
    }

    public PendingIntent getActivities(Context context, int requestCode, Intent[] intents, int flags, Bundle options) {
        return PendingIntent.getActivities(context, requestCode, intents, flags, options);
    }

    public PendingIntent getBroadcast(Context context, int requestCode, Intent intent, int flags) {
        return PendingIntent.getBroadcast(context, requestCode, intent, flags);
    }

    public PendingIntent getService(Context context, int requestCode, Intent intent, int flags) {
        return PendingIntent.getService(context, requestCode, intent, flags);
    }

    public PendingIntent getForegroundService(Context context, int requestCode, Intent intent, int flags) {
        return PendingIntent.getForegroundService(context, requestCode, intent, flags);
    }
}

