package com.wada811.immutablependingintent.lint

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

@Suppress("UnstableApiUsage")
class UseRawPendingIntentDetector : Detector(), SourceCodeScanner {
    companion object {
        @JvmField
        val ISSUE: Issue = Issue.create(
            // ID: used in @SuppressLint warnings etc
            id = "UseRawPendingIntent",
            // Title -- shown in the IDE's preference dialog, as category headers in the Analysis results window, etc
            briefDescription = "Not use ImmutablePendingIntent or MutablePendingIntent",
            // Full explanation of the issue; you can use some markdown markup such as `monospace`, *italic*, and **bold**.
            explanation = """
                You should use ImmutablePendingIntent or MutablePendingIntent for avoid not to forget specifying mutability flag.
                
                The reason is that apps targeting Android 12 and higher must specify either `FLAG_IMMUTABLE` or `FLAG_MUTABLE` when constructing a `PendingIntent`.
                """,
            moreInfo = "https://developer.android.com/about/versions/12/behavior-changes-12#pending-intent-mutability",
            category = Category.SECURITY,
            priority = 5,
            severity = Severity.ERROR,
            androidSpecific = true,
            implementation = Implementation(
                UseRawPendingIntentDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }

    override fun getApplicableMethodNames(): List<String> = listOf(
        "getActivity",
        "getActivities",
        "getBroadcast",
        "getService",
        "getForegroundService",
    )

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        if (!context.evaluator.isMemberInClass(method, "android.app.PendingIntent")) {
            return
        }
        context.report(
            Incident(
                issue = ISSUE,
                scope = node,
                location = context.getLocation(node),
                message = "Using raw PendingIntent, not ImmutablePendingIntent or MutablePendingIntent",
                fix = quickFix(node)
            )
        )
    }

    private fun quickFix(node: UCallExpression): LintFix {
        val lintFixToImmutablePendingIntent = node.toLintFix("ImmutablePendingIntent")
        val lintFixToMutablePendingIntent = node.toLintFix("MutablePendingIntent")
        return fix()
            .group()
            .add(lintFixToImmutablePendingIntent)
            .add(lintFixToMutablePendingIntent)
            .build()
    }

    private fun UCallExpression.toLintFix(className: String): LintFix {
        val callSource = uastParent?.sourcePsi?.text
        return fix()
            .replace()
            .text(callSource)
            .shortenNames()
            .reformat(true)
            .with("$className.${methodName}(${valueArguments.map { it.sourcePsi?.text }.joinToString()})")
            .build()
    }
}
