@file:Suppress("unused")

package com.mskj.mercer.buildsrc

import java.io.File

/**
 * 遍历展开文件夹
 */
fun unfoldTraverseFolder(src: File): List<File> {

    val values = arrayListOf<File>()

    if (!src.exists()) {
        return values
    }

    if (src.isFile) {
        values.add(src)
        return values
    }

    val folders = arrayListOf<File>()
    folders.add(src)
    var index = 0
    var folder: File
    var hasNext: Boolean = index < folders.size
    while (hasNext) {
        folder = folders[index++]
        if (!folder.exists()) {
            continue
        }
        val files = folder.listFiles() ?: continue
        for (file in files) {
            if (file.isDirectory) {
                folders.add(file)
                continue
            }
            values.add(file)
        }
        hasNext = index < folders.size
    }
    return values
}