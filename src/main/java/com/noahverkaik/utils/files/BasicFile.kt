package com.noahverkaik.utils.files

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException

open class BasicFile(main: Plugin, filename: String) : CustomFile(main, filename) {
    override fun save() {
        if (!File(plugin.dataFolder, fileName).exists()) {
            try {
                File(plugin.dataFolder, fileName).createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        try {
            config?.save(File(plugin.dataFolder, fileName))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun reload() {
        if (!File(plugin.dataFolder, fileName).exists()) {
            try {
                plugin.saveResource(fileName, false)
            } catch (e: Exception) {
                try {
                    File(plugin.dataFolder, fileName).createNewFile()
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
            }
        }
        config = YamlConfiguration.loadConfiguration(File(plugin.dataFolder, fileName))
    }

    override fun reset() {
        if (File(plugin.dataFolder, fileName).exists()) {
            File(plugin.dataFolder, fileName).delete()
        }
        reload()
    }

    init {
        reload()
    }
}