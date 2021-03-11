package com.noahverkaik.bounties

import com.noahverkaik.bounties.commands.AddBountyCommand
import com.noahverkaik.bounties.commands.StartBountyCommand
import com.noahverkaik.bounties.commands.StopBountyCommand
import com.noahverkaik.bounties.events.BountyChatEventHandler
import com.noahverkaik.bounties.events.BountyEntityDamageByEntityEventHandler
import com.noahverkaik.utils.files.BasicFile
import com.noahverkaik.utils.files.MessagesFile
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*

class Bounties : JavaPlugin() {

    lateinit var tabPrefix: String
    lateinit var chatPrefix: String

    var bountiesEnabled = false

    var claimEvery: Long = 1800000

    var bountyCommands: MutableList<String> = mutableListOf()

    val activeBounties: MutableList<UUID> = mutableListOf()
    val claimedBounties: HashMap<UUID, Long> = HashMap<UUID, Long>()

    override fun onEnable() {
        saveDefaultConfig()

        langFile = MessagesFile(this, "lang.yml")

        if (File(dataFolder, "temp.yml").exists()) {
            logger.info("Loading bounties from pre-restart...")
            bountiesEnabled = true

            val tempFile = BasicFile(this, "temp.yml")
            try {
                val oldBounties = tempFile.config?.get("bounties", mutableListOf<String>()) as Collection<String>
                oldBounties.forEach {
                    activeBounties.add(UUID.fromString(it))
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            File(dataFolder, "temp.yml").delete()
            logger.info("Loaded ${activeBounties.size} bounties and cleaned up temp.yml.")
        }

        reloadSettings()

        Bukkit.getPluginCommand("addbounty")?.setExecutor(AddBountyCommand(this))
        Bukkit.getPluginCommand("startbounty")?.setExecutor(StartBountyCommand(this))
        Bukkit.getPluginCommand("stopbounty")?.setExecutor(StopBountyCommand(this))

        BountyPlaceholderExpansion(this).register()
        server.pluginManager.registerEvents(BountyChatEventHandler(this), this)
        server.pluginManager.registerEvents(BountyEntityDamageByEntityEventHandler(this), this)
    }

    fun reloadSettings() {
        claimEvery = config.getLong("claim-every", claimEvery)
        bountyCommands.addAll(config.getStringList("commands"))

        tabPrefix = langFile.loadMessage("player.bountied-prefix.tab").color().build()
        chatPrefix = langFile.loadMessage("player.bountied-prefix.chat").color().build()
    }

    override fun onDisable() {
        if (bountiesEnabled) {
            val tempFile = BasicFile(this, "temp.yml")
            val savedBounties = mutableListOf<String>()
            activeBounties.forEach {
                savedBounties.add(it.toString())
            }
            tempFile.config?.set("bounties", savedBounties)
            tempFile.save()
        }
    }

    companion object {
        lateinit var langFile: MessagesFile
    }

}

fun CommandSender.sendConfigMessage(path: String) {
    this.sendMessage(Bounties.langFile.loadMessage(path).color().build())
}

fun CommandSender.sendConfigMessage(path: String, vararg placeholders: AbstractMap.SimpleEntry<String, String>) {
    this.sendMessage(Bounties.langFile.loadMessage(path).placeholders(*placeholders).color().build())
}
