package com.otus.homework.app.testRunner

import org.robolectric.RobolectricTestRunner
import org.robolectric.internal.bytecode.SandboxConfig

class ChessTaskPresenterTestRunner(parameter: Class<*>) : RobolectricTestRunner(parameter) {
    protected override fun addShadows(
        shadowClasses: MutableList<Class<*>>?,
        annotation: SandboxConfig?
    ) {
        super.addShadows(shadowClasses, annotation)

    }
}