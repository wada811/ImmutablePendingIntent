package com.wada811.immutablependingintent.test

import android.app.ImmutablePendingIntent
import android.app.MutablePendingIntent
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf

@RunWith(AndroidJUnit4::class)
class PendingIntentFlagsTest {
    @Test
    fun testIsImmutable_withNoMutabilityFlags() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val requestCode = 0
        val intent = Intent()
        val flags = PendingIntent.FLAG_ONE_SHOT
        val pendingIntent = ImmutablePendingIntent.getActivity(context, requestCode, intent, flags)
        val shadowPendingIntent = shadowOf(pendingIntent)
        Truth.assertThat(shadowPendingIntent.flags).isEqualTo(PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
        Truth.assertThat(shadowPendingIntent.flags and PendingIntent.FLAG_IMMUTABLE).isEqualTo(PendingIntent.FLAG_IMMUTABLE)
        Truth.assertThat(shadowPendingIntent.flags and PendingIntent.FLAG_MUTABLE).isEqualTo(0)
    }

    @Test
    fun testIsImmutable_withMutableFlag() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val requestCode = 0
        val intent = Intent()
        val flags = PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE
        val pendingIntent = ImmutablePendingIntent.getActivity(context, requestCode, intent, flags)
        val shadowPendingIntent = shadowOf(pendingIntent)
        Truth.assertThat(shadowPendingIntent.flags).isEqualTo(PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
        Truth.assertThat(shadowPendingIntent.flags and PendingIntent.FLAG_IMMUTABLE).isEqualTo(PendingIntent.FLAG_IMMUTABLE)
        Truth.assertThat(shadowPendingIntent.flags and PendingIntent.FLAG_MUTABLE).isEqualTo(0)
    }

    @Test
    fun testIsImmutable_withBothMutabilityFlags() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val requestCode = 0
        val intent = Intent()
        val flags = PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = ImmutablePendingIntent.getActivity(context, requestCode, intent, flags)
        val shadowPendingIntent = shadowOf(pendingIntent)
        Truth.assertThat(shadowPendingIntent.flags).isEqualTo(PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
        Truth.assertThat(shadowPendingIntent.flags and PendingIntent.FLAG_IMMUTABLE).isEqualTo(PendingIntent.FLAG_IMMUTABLE)
        Truth.assertThat(shadowPendingIntent.flags and PendingIntent.FLAG_MUTABLE).isEqualTo(0)
    }

    @Test
    fun testIsMutable_withNoMutabilityFlags() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val requestCode = 0
        val intent = Intent()
        val flags = PendingIntent.FLAG_ONE_SHOT
        val pendingIntent = MutablePendingIntent.getActivity(context, requestCode, intent, flags)
        val shadowPendingIntent = shadowOf(pendingIntent)
        Truth.assertThat(shadowPendingIntent.flags).isEqualTo(PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE)
        Truth.assertThat(shadowPendingIntent.flags and PendingIntent.FLAG_MUTABLE).isEqualTo(PendingIntent.FLAG_MUTABLE)
        Truth.assertThat(shadowPendingIntent.flags and PendingIntent.FLAG_IMMUTABLE).isEqualTo(0)
    }

    @Test
    fun testIsMutable_withImmutableFlag() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val requestCode = 0
        val intent = Intent()
        val flags = PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = MutablePendingIntent.getActivity(context, requestCode, intent, flags)
        val shadowPendingIntent = shadowOf(pendingIntent)
        Truth.assertThat(shadowPendingIntent.flags).isEqualTo(PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE)
        Truth.assertThat(shadowPendingIntent.flags and PendingIntent.FLAG_MUTABLE).isEqualTo(PendingIntent.FLAG_MUTABLE)
        Truth.assertThat(shadowPendingIntent.flags and PendingIntent.FLAG_IMMUTABLE).isEqualTo(0)
    }

    @Test
    fun testIsMutable_withBothMutabilityFlags() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val requestCode = 0
        val intent = Intent()
        val flags = PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_MUTABLE
        val pendingIntent = MutablePendingIntent.getActivity(context, requestCode, intent, flags)
        val shadowPendingIntent = shadowOf(pendingIntent)
        Truth.assertThat(shadowPendingIntent.flags).isEqualTo(PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE)
        Truth.assertThat(shadowPendingIntent.flags and PendingIntent.FLAG_MUTABLE).isEqualTo(PendingIntent.FLAG_MUTABLE)
        Truth.assertThat(shadowPendingIntent.flags and PendingIntent.FLAG_IMMUTABLE).isEqualTo(0)
    }
}
