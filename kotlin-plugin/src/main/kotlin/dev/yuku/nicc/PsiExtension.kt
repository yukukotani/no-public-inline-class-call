package dev.yuku.nicc

import org.jetbrains.kotlin.com.intellij.psi.PsiElement

fun PsiElement.sourcePosition(): String {
    return containingFile.virtualFile.path + ":" + lineNumber()
}

fun PsiElement.lineNumber(): Int {
    val doc = containingFile.viewProvider.document ?: throw IllegalStateException("Document for containing file not found.")
    return doc.getLineNumber(textOffset) + 1
}