package com.vilkov.autoCleaning
import java.io.File
import java.io.FileReader
import java.util.Properties


fun main(args: Array<String>) {
    Cleaner().startingAutoClear()
}

class Cleaner() {
    fun startingAutoClear() {
        val reader = FileReader("src/main/resources/application.properties")
        val prop = Properties()
        prop.load(reader)
        val rootDirectories = foundAllRootDirectory(prop)
        for (rootDirectory in rootDirectories) {
            recursiveDelete(File(rootDirectory))
        }

    }
}

private fun recursiveDelete(file: File) {
    if (!file.exists())
        return

    if (file.isDirectory)
        for (f in file.listFiles()!!) {
            recursiveDelete(f)
        }
    if (file.delete()) {
        println("Файл(директория) $file удален(а)")
    } else {
        println("Не возможно удалить $file")
    }
}

private fun foundAllRootDirectory(properties: Properties): List<String> {
    val keys: ArrayList<String> = ArrayList()
    for (key in properties.propertyNames()) {
        keys.add(key.toString())
    }
    val result: ArrayList<String> = ArrayList()
    for (key in keys) {
        result.add(properties.getProperty(key))
    }
    return result;
}