package com.mskj.mercer.buildsrc

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.io.FileOutputStream

class MergeFinderPlugin : Plugin<Project> {

    companion object {
        private const val NAME_PACKAGE_NAME = "com.mskj.mercer"
        private const val NAME_APT_PACKAGE_NAME = "${NAME_PACKAGE_NAME}.apt"
        private const val NAME_CORE_PACKAGE_NAME = "${NAME_PACKAGE_NAME}.core"
        private const val CLASS_FILE_SUFFIX = ".class"
    }

    override fun apply(project: Project) {
        val list = arrayListOf<OnGatherProvider>()
        list.add(object : OnCommonGatherProvider("com.mskj.mercer.apt.ViewBindingFinder") {
            override fun handle(outputFile: File) {
                super.handle(outputFile)
                val names = names().map { "com/mskj/mercer/apt/$it" }
                val code =
                    ViewBindingFinderDump.dump("com/mskj/mercer/core/ViewBindingFinder", names)
                val fileName = "com/mskj/mercer/core/ViewBindingFinder.class"
                val file = File(outputFile, fileName)
                if (!file.parentFile.exists()) {
                    file.parentFile.mkdirs()
                }
                if (file.exists()) {
                    file.delete()
                }
                val fos = FileOutputStream(file)
                fos.write(code)
                fos.close()
            }
        })
        list.add(object : OnCommonGatherProvider("com.mskj.mercer.apt.ViewModelFinder") {
            override fun handle(outputFile: File) {
                super.handle(outputFile)
                val names = names().map { "com/mskj/mercer/apt/$it" }
                val code = ViewModelFinderDump.dump("com/mskj/mercer/core/ViewModelFinder", names)
                val fileName = "com/mskj/mercer/core/ViewModelFinder.class"
                val file = File(outputFile, fileName)
                if (!file.parentFile.exists()) {
                    file.parentFile.mkdirs()
                }
                if (file.exists()) {
                    file.delete()
                }
                val fos = FileOutputStream(file)
                fos.write(code)
                fos.close()
            }
        })
        project.extensions.findByType(BaseExtension::class.java)
            ?.registerTransform(MergeFinderTransform(list))
    }
}