package com.wada811.immutablependingintent.test

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.java
import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.wada811.immutablependingintent.lint.UseRawPendingIntentDetector
import org.junit.Test
import java.io.File

@Suppress("UnstableApiUsage")
class UseRawPendingIntentDetectorTest {
    @Test
    fun testDetectJava() {
        lint()
            .files(
                java(
                    File("src/main/java/com/wada811/immutablependingintent/test/RawPendingIntentJava.java").readText()
                ).indented()
            )
            .issues(UseRawPendingIntentDetector.ISSUE)
            .run()
            .expect(
                File("src/test/resources/UseRawPendingIntentDetectorTest/testDetectJava.txt").readText()
            )
    }

    @Test
    fun testDetectKotlin() {
        lint()
            .files(
                kotlin(
                    File("src/main/java/com/wada811/immutablependingintent/test/RawPendingIntentKotlin.kt").readText()
                ).indented()
            )
            .issues(UseRawPendingIntentDetector.ISSUE)
            .run()
            .expect(
                File("src/test/resources/UseRawPendingIntentDetectorTest/testDetectKotlin.txt").readText()
            )
    }

    @Test
    fun testQuickFix() {
        lint()
            .files(
                kotlin(
                    File("src/main/java/com/wada811/immutablependingintent/test/RawPendingIntentKotlin.kt").readText()
                ).indented()
            )
            .issues(UseRawPendingIntentDetector.ISSUE)
            .run()
            .expectFixDiffs(
                File("src/test/resources/UseRawPendingIntentDetectorTest/testQuickFix.txt").readText()
            )
    }

    @Test
    fun testNotDetectJava() {
        lint()
            .files(
                java(
                    File("src/main/java/com/wada811/immutablependingintent/test/ImmutablePendingIntentJava.java").readText()
                ).indented(),
                kotlin(
                    File("../ImmutablePendingIntent/src/main/java/android/app/ImmutablePendingIntent.kt").readText()
                ).indented(),
            )
            .issues(UseRawPendingIntentDetector.ISSUE)
            .run()
            .expect(
                File("src/test/resources/UseRawPendingIntentDetectorTest/testNotDetectJava.txt").readText()
            )
    }

    @Test
    fun testNotDetectKotlin() {
        lint()
            .files(
                kotlin(
                    File("src/main/java/com/wada811/immutablependingintent/test/ImmutablePendingIntentKotlin.kt").readText()
                ).indented(),
                kotlin(
                    File("../ImmutablePendingIntent/src/main/java/android/app/ImmutablePendingIntent.kt").readText()
                ).indented(),
            )
            .issues(UseRawPendingIntentDetector.ISSUE)
            .run()
            .expect(
                File("src/test/resources/UseRawPendingIntentDetectorTest/testNotDetectKotlin.txt").readText()
            )
    }
}
