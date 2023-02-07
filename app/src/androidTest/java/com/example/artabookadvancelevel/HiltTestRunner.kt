package com.example.artabookadvancelevel

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class HiltTestRunner:AndroidJUnitRunner() {
    //burada className duzeltdiyimiz HiltApplication adina beraber edirik
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestActivity::class.java.name, context)
    }
}