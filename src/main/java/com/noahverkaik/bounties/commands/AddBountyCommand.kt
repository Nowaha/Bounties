package com.noahverkaik.bounties.commands

import com.noahverkaik.bounties.Bounties
import com.noahverkaik.bounties.sendConfigMessage
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.AbstractMap

class AddBountyCommand(private val main: Bounties) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            sender.sendConfigMessage("commands.error.cannot-use")
            return true
        }

        if (!main.bountiesEnabled) {
            sender.sendConfigMessage("commands.error.bounties-are-disabled")
            return true
        }

        if (args.isNotEmpty()) {
            val player: Player? = Bukkit.getPlayer(args[0])

            if (player != null) {
                newBounty(player)
                sender.sendConfigMessage("commands.added-bounty", AbstractMap.SimpleEntry("%player%", player.name))
            } else {
                sender.sendConfigMessage("commands.error.player-not-found")
            }
        }

        return true
    }

    fun newBounty(player: Player) {
        if (!main.bountiesEnabled) {
            return
        }

        if (!main.activeBounties.contains(player.uniqueId)) {
            main.activeBounties.add(player.uniqueId)

            player.sendConfigMessage("player.bountied")

            Bukkit.broadcastMessage(
                Bounties.langFile.loadMessage("broadcast.bountied").placeholders(
                    AbstractMap.SimpleEntry("%player%", player.name)
                ).color().build()
            )
        }

        applyBounty(player)
    }

    fun applyBounty(player: Player) {
        if (!main.bountiesEnabled) {
            return
        }

        if (!main.activeBounties.contains(player.uniqueId)) {
            main.activeBounties.add(player.uniqueId)
        }
    }

}