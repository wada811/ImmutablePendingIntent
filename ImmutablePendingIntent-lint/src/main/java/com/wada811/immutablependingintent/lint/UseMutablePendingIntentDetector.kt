package com.wada811.immutablependingintent.lint

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.psiUtil.parents
import org.jetbrains.kotlin.utils.addToStdlib.cast
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UastFacade

@Suppress("UnstableApiUsage")
class UseMutablePendingIntentDetector : Detector(), SourceCodeScanner {
    companion object {
        val ISSUE = Issue.create(
            id = "UseMutablePendingIntentNoReason",
            briefDescription = "Use MutablePendingIntent without UseMutablePendingIntent",
            explanation = "You should specify `UseMutablePendingIntent` annotation for explaining reason to use MutablePendingIntent.",
            category = Category.CORRECTNESS,
            priority = 5,
            severity = Severity.ERROR,
            androidSpecific = true,
            implementation = Implementation(
                UseMutablePendingIntentDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )

        private const val USE_ANNOTATION = "com.wada811.immutablependingintent.UseMutablePendingIntent"
    }

    override fun getApplicableMethodNames(): List<String> = listOf(
        "getActivity",
        "getActivities",
        "getBroadcast",
        "getService",
        "getForegroundService",
    )

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        if (!context.evaluator.isMemberInClass(method, "com.wada811.immutablependingintent.MutablePendingIntent")) {
            return
        }
        val (callSite, hasUseAnnotation) = if (isKotlin(node.uastParent?.sourcePsi)) {
            val callSite: KtNamedFunction = node.uastParent?.sourcePsi?.parents?.firstOrNull { it is KtNamedFunction }?.cast() ?: return
            val hasUseAnnotation = callSite.annotationEntries
                .mapNotNull { UastFacade.convertElement(it, null) as? UAnnotation }
                .any { it.qualifiedName == USE_ANNOTATION }
            callSite to hasUseAnnotation
        } else {
            val callSite: PsiMethod = node.uastParent?.sourcePsi?.parents?.firstOrNull { it is PsiMethod }?.cast() ?: return
            val hasUseAnnotation = callSite.hasAnnotation(USE_ANNOTATION)
            callSite to hasUseAnnotation
        }
        if (!hasUseAnnotation) {
            context.report(
                Incident(
                    issue = ISSUE,
                    scope = node,
                    location = context.getLocation(node),
                    message = "Using MutablePendingIntent without UseMutablePendingIntent annotation",
                    fix = quickFix(context, node, callSite)
                )
            )
        }
    }

    private fun quickFix(context: JavaContext, node: UCallExpression, callSite: PsiElement): LintFix {
        return fix()
            .group()
            .add(annotateFix(context, node, callSite))
            .build()
    }

    private fun annotateFix(context: JavaContext, node: UCallExpression, callSite: PsiElement): LintFix {
        return fix()
            .name("Add '$USE_ANNOTATION' annotation to ${node.methodName}")
            .replace()
            .range(context.getLocation(callSite))
            .beginning()
            .with(
                """@$USE_ANNOTATION(reason = $USE_ANNOTATION.Reason.YouMustSelectReason)
    """ // break line for testing
            )
            .shortenNames()
            .reformat(true)
            .autoFix()
            .build()
    }
}
