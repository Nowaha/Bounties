package com.noahverkaik.utils.files

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin

/**
 * @author Noah Verkaik
 * This class can be extended to create custom configuration files.
 */
abstract class CustomFile(var plugin: Plugin, var fileName: String) {
    var config: YamlConfiguration? = null
    abstract fun save()
    abstract fun reload()
    abstract fun reset()
}