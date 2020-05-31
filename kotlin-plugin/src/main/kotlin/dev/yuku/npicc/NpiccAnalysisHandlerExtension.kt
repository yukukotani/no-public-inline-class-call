package dev.yuku.npicc

import org.jetbrains.kotlin.analyzer.AnalysisResult
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.com.intellij.openapi.project.Project
import org.jetbrains.kotlin.descriptors.CallableDescriptor
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.js.resolve.diagnostics.findPsi
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.psiUtil.collectDescendantsOfType
import org.jetbrains.kotlin.psi.psiUtil.getStrictParentOfType
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.BindingTrace
import org.jetbrains.kotlin.resolve.calls.callUtil.getResolvedCallWithAssert
import org.jetbrains.kotlin.resolve.jvm.extensions.AnalysisHandlerExtension
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

class NiccAnalysisHandlerExtension(private val log: MessageCollector, private val severity: CompilerMessageSeverity) : AnalysisHandlerExtension {

    override fun analysisCompleted(
        project: Project,
        module: ModuleDescriptor,
        bindingTrace: BindingTrace,
        files: Collection<KtFile>
    ): AnalysisResult? {
        files.forEach { checkFile(it, bindingTrace.bindingContext) }
        return null
    }

    private fun checkFile(file: KtFile, context: BindingContext) {
        val callExpressions = file.collectDescendantsOfType<KtCallExpression>()
        callExpressions.filter { isExternalCall(it, context) }.forEach {
            logExternalCall(it)
        }
    }

    private fun isExternalCall(expression: KtCallExpression, context: BindingContext): Boolean {
        val call = expression.getResolvedCallWithAssert(context).resultingDescriptor
        if (!call.isInlineClassConstructorCall()) {
            return false
        }

        val calledClass = call.constructedClass.findPsi() ?: throw IllegalStateException("Cannot resolve called class.")
        val containingClass = expression.getStrictParentOfType<KtClass>() ?: return true

        return !containingClass.isEquivalentTo(calledClass)
    }

    private fun logExternalCall(expression: KtCallExpression) {
        log.report(severity, "External inline class constructor call is detected: ${expression.sourcePosition()}")
    }
}

@OptIn(ExperimentalContracts::class)
internal fun CallableDescriptor.isInlineClassConstructorCall(): Boolean {
    contract {
        returns(true) implies (this@isInlineClassConstructorCall is ConstructorDescriptor)
    }
    return this is ConstructorDescriptor && this.constructedClass.isInline
}