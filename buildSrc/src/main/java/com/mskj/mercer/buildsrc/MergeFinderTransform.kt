package com.mskj.mercer.buildsrc

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import java.io.File
import java.util.jar.JarEntry
import java.util.jar.JarFile

// 采集
class MergeFinderTransform(private val gathers: List<OnGatherProvider>) : Transform() {

    override fun getName(): String = javaClass.simpleName

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> =
        TransformManager.CONTENT_CLASS

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> = hashSetOf(
        QualifiedContent.Scope.PROJECT,
        QualifiedContent.Scope.SUB_PROJECTS
    )

    // 暂不支持增量
    override fun isIncremental(): Boolean = false

    override fun transform(transformInvocation: TransformInvocation?) {
        super.transform(transformInvocation)

        if (transformInvocation == null) {
            return
        }
        if (transformInvocation.isIncremental) {
            return
        }

        val outputProvider: TransformOutputProvider = transformInvocation.outputProvider

        transformInvocation
            .inputs
            .flatMap { it.jarInputs }
            .map { jarInput: JarInput ->
                migrateJar(jarInput, outputProvider)
            }
            .onEach { file ->
                val jarFile = JarFile(file)
                for (gather in gathers) {
                    val entry = findEntry(
                        jarFile, gather.key()
                            .replace(".", "/"), 0
                    ) ?: continue
                    gather.addName(entry.name.split("/").last().split(".").first())
                }
            }

        var parentFile: File? = null

        transformInvocation
            .inputs
            .flatMap { it.directoryInputs }
            .map { directoryInput ->
                migrateDirectory(directoryInput, outputProvider)
            }
            .onEach { dest ->
                val file = saveOutputPathFile(dest)
                if (file != null && parentFile == null) {
                    parentFile = file
                }
            }
            .flatMap { dest ->
                val rootPath = dest.absolutePath.plus(File.separator)
                unfoldTraverseFolder(dest)
                    .map { it.absolutePath.replace(rootPath, "") }
            }
            .onEach {
                for (gather in gathers) {
                    if (it.startsWith(gather.key().replace(".", File.separator))) {
                        gather.addName(it.split(File.separator).last().split(".").first())
                    }
                }
            }

        gathers
            .asSequence()
            .filter { it.names().isNotEmpty() }
            .onEach {
                it.handle(parentFile!!)
            }
            .toList()

    }

    private fun saveOutputPathFile(dest: File): File? {
        val folder = unfoldTraverseFolder(dest)
        folder.find { it.absolutePath.endsWith(".kotlin_module") } ?: return dest
        return null
    }

    @Suppress("SameParameterValue")
    private fun findEntry(jarFile: JarFile, name: String, flag: Int): JarEntry? {
        val entries = jarFile.entries()
        while (entries.hasMoreElements()) {
            val jarEntry = entries.nextElement()
            if (flag == 0) {
                if (jarEntry.name.startsWith(name)) {
                    return jarEntry
                }
            } else {
                if (jarEntry.name == name) {
                    return jarEntry
                }
            }
        }
        return null
    }

    // 迁徙
    private fun migrateJar(jarInput: JarInput, outputProvider: TransformOutputProvider): File {
        var destName = jarInput.name
        val md5Name = DigestUtils.md5Hex(jarInput.file.absolutePath)
        if (destName.endsWith(".jar")) {
            destName = destName.substring(0, destName.length - 4)
        }
        val dest = outputProvider.getContentLocation(
            "${destName}_${md5Name}",
            jarInput.contentTypes, jarInput.scopes, Format.JAR
        )
        FileUtils.copyFile(jarInput.file, dest)
        return dest
    }

    private fun migrateDirectory(
        directoryInput: DirectoryInput,
        outputProvider: TransformOutputProvider
    ): File {
        val dir = directoryInput.file
        val dest = outputProvider.getContentLocation(
            directoryInput.name,
            directoryInput.contentTypes,
            directoryInput.scopes,
            Format.DIRECTORY
        )
        FileUtils.copyDirectory(dir, dest)
        return dest
    }

}