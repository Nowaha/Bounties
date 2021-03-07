package com.noahverkaik.bounties.commands

import com.noahverkaik.bounties.Bounties
import com.noahverkaik.bounties.sendConfigMessage
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class StartBountyCommand(private val main: Bounties) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender.hasPermission("bounty.admin")) {
            main.bountiesEnabled = true
            main.activeBounties.clear()
            main.claimedBounties.clear()

            sender.sendConfigMessage("commands.enabled-bounties")
        } else {
            sender.sendConfigMessage("commands.error.cannot-use")
        }

        return true
    }

}