package com.noahverkaik.utils.files

import org.bukkit.ChatColor
import org.bukkit.plugin.Plugin
import java.util.*
import java.util.AbstractMap.SimpleEntry

class MessagesFile(main: Plugin, filename: String) : BasicFile(main, filename) {
    fun loadRawMessage(path: String): String {
        return config?.getString(path, path)!!
    }

    fun loadRawMessages(path: String): ArrayList<String> {
        return ArrayList(config?.getStringList(path))
    }

    fun loadMessage(path: String): MessageBuilder {
        return MessageBuilder(path)
    }

    fun loadMessages(path: String): MessageListBuilder {
        return MessageListBuilder(path)
    }

    inner class MessageBuilder(path: String) {
        private var message: String
        fun color(): MessageBuilder {
            message = ChatColor.translateAlternateColorCodes('&', message)
            return this
        }

        fun placeholders(vararg placeholders: SimpleEntry<String, String>): MessageBuilder {
            for (placeholder in placeholders) {
                message = message.replace(placeholder.key, placeholder.value)
            }
            return this
        }

        fun build(): String {
            return message
        }

        init {
            message = loadRawMessage(path)
        }
    }

    inner class MessageListBuilder(path: String) {
        private var messages: ArrayList<String>
        fun color(): MessageListBuilder {
            val newList = ArrayList<String>()
            for (line in messages) {
                newList.add(ChatColor.translateAlternateColorCodes('&', line))
            }
            messages = newList
            return this
        }

        fun placeholders(vararg placeholders: SimpleEntry<String, String>): MessageListBuilder {
            val newList = ArrayList<String>()
            messages.forEachIndexed { index, line ->

            }
            for (line in messages) {
                var newLine = line
                placeholders.forEach { placeholder ->
                    newLine = line.replace(placeholder.key, placeholder.value)
                }
                newList.add(ChatColor.translateAlternateColorCodes('&', newLine))
            }
            messages = newList
            return this
        }

        fun build(): ArrayList<String> {
            return messages
        }

        init {
            messages = loadRawMessages(path)
        }
    }
}