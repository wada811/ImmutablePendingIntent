package com.wada811.immutablependingintent.test

import com.android.tools.lint.checks.infrastructure.TestFiles.java
import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.wada811.immutablependingintent.lint.UseMutablePendingIntentDetector
import org.junit.Test
import java.io.File

@Suppress("UnstableApiUsage")
class UseMutablePendingIntentDetectorTest {
    @Test
    fun testDetectJava() {
        lint()
            .files(
                java(
                    File("src/main/java/com/wada811/immutablependingintent/test/MutablePendingIntentJava.java").readText()
                ).indented(),
                kotlin(
                    File("../ImmutablePendingIntent/src/main/java/android/app/MutablePendingIntent.kt").readText()
                ).indented(),
            )
            .issues(UseMutablePendingIntentDetector.ISSUE)
            .run()
            .expect(
                File("src/test/resources/UseMutablePendingIntentDetectorTest/testDetectJava.txt").readText()
            )
    }

    @Test
    fun testDetectKotlin() {
        lint()
            .files(
                kotlin(
                    File("src/main/java/com/wada811/immutablependingintent/test/MutablePendingIntentKotlin.kt").readText()
                ).indented(),
                kotlin(
                    File("../ImmutablePendingIntent/src/main/java/android/app/MutablePendingIntent.kt").readText()
                ).indented(),
            )
            .issues(UseMutablePendingIntentDetector.ISSUE)
            .run()
            .expect(
                File("src/test/resources/UseMutablePendingIntentDetectorTest/testDetectKotlin.txt").readText()
            )
    }

    @Test
    fun testQuickFixJava() {
        lint()
            .files(
                java(
                    File("src/main/java/com/wada811/immutablependingintent/test/MutablePendingIntentJava.java").readText()
                ).indented(),
                kotlin(
                    File("../ImmutablePendingIntent/src/main/java/android/app/MutablePendingIntent.kt").readText()
                ).indented(),
            )
            .issues(UseMutablePendingIntentDetector.ISSUE)
            .run()
            .expectFixDiffs(
                File("src/test/resources/UseMutablePendingIntentDetectorTest/testQuickFixJava.txt").readText()
            )
    }

    @Test
    fun testQuickFixKotlin() {
        lint()
            .files(
                kotlin(
                    File("src/main/java/com/wada811/immutablependingintent/test/MutablePendingIntentKotlin.kt").readText()
                ).indented(),
                kotlin(
                    File("../ImmutablePendingIntent/src/main/java/android/app/MutablePendingIntent.kt").readText()
                ).indented(),
            )
            .issues(UseMutablePendingIntentDetector.ISSUE)
            .run()
            .expectFixDiffs(
                File("src/test/resources/UseMutablePendingIntentDetectorTest/testQuickFixKotlin.txt").readText()
            )
    }
}
