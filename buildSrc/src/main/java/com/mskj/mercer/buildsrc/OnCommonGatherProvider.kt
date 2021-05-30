package com.mskj.mercer.buildsrc

import java.io.File

abstract class OnCommonGatherProvider(
    private val key: String
) : OnGatherProvider {

    private val classNames = arrayListOf<String>()

    override fun key(): String = key

    override fun addName(name: String) {
        classNames.add(name)
    }

    override fun names(): List<String> = classNames

    override fun handle(outputFile: File) {
    }

}