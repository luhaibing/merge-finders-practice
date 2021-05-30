package com.mskj.mercer.buildsrc

import java.io.File

interface OnGatherProvider {

    fun key(): String

    fun addName(name: String)

    fun names(): List<String>

    fun handle(outputFile: File)

}